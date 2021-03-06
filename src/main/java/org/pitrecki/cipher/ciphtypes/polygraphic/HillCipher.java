package org.pitrecki.cipher.ciphtypes.polygraphic;

import org.pitrecki.cipher.ciphtypes.Cipher;
import org.pitrecki.cipher.interfaces.AbstractEncryptMatrixGenerator;
import org.pitrecki.cipher.utils.CryptVariant;
import org.pitrecki.cipher.utils.EncryptMatrixGenerator;
import org.pitrecki.cipher.utils.math.Algorithms;
import org.pitrecki.cipher.utils.math.Matrix;
import org.pitrecki.cipher.utils.math.MatrixException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * In classical cryptography, the Hill cipher is a polygraphic substitution cipher based on linear algebra.
 * Invented by Lester S. Hill in 1929, it was the first polygraphic cipher in which it was practical (though barely)
 * to operate on more than three symbols at once. The following discussion assumes an elementary knowledge of matrices.
 *
 * Please watch for cryptographic matrix, which is the reason for the failure of the inversion of the determinant and the
 * length of the module.
 *
 * @author Piotr 'pitrecki' Nowak
 *
 * Created by Pitrecki on 2016-11-10.
 */
public class HillCipher extends Cipher implements AbstractEncryptMatrixGenerator
{

    //Numbers 221 is Ý,only true if word length is ODD!
    private final short SPECIAL_SIGN = 221;
    //equals 26
    private final int MOD_VAL = ASCII_TABLE.length;
    /**
     * global iterator
     * {@link #cipherProcessing(String, CryptVariant)}
     */
    private int ITERATOR = 0;

     /**
     * In this case constructor generate NxN size matrix with random values
     * @param size square matrix size
     */

    public HillCipher(int size)  {
        generateEncryptMatrix(new EncryptMatrixGenerator.EncryptMatrixGeneratorBuilder<>(Integer.class).withSize(size).build());
    }

    public HillCipher(Matrix encryptMatrix) throws IllegalArgumentException {
        if (encryptMatrix.getData().length != encryptMatrix.getData()[0].length)
            throw new IllegalArgumentException("Key size not matched!");

        setEncryptMatrix(encryptMatrix);

    }

    public HillCipher(Integer[][] key) throws IllegalArgumentException {
        this(new Matrix<>(key));
    }

    @Override
    public void generateEncryptMatrix(EncryptMatrixGenerator generator) {
        int size = generator.getSize();
        Integer[] values = new Integer[size];
        for (int i = 0; i < size*size; i++)
            values[i] = generateRandomValue();

        generator.fill(values);
        setEncryptMatrix(generator.getMatrix());

    }

    private int generateRandomValue() {
        return new Random().nextInt(ASCII_TABLE.length);
    }

    /**
     *
     * @param inputText is text to encrypt
     */

    @Override
    public void encrypt(String inputText) {
        try {
            cipherProcessing(inputText, CryptVariant.ENCRYPT);
        } catch (MatrixException e) {
            e.printStackTrace();
        }
    }


    private List<Integer> modDivide(List<Integer> data) {
        return data.stream()
                .map(integer -> integer%MOD_VAL)
                .collect(Collectors.toList());
    }

    /**
     *
     * @param inputText text to decrypt
     */

    @Override
    public void decrypt(String inputText) {
        try {
            cipherProcessing(inputText, CryptVariant.DECRYPT);
        } catch (MatrixException e) {
            e.printStackTrace();
        }
    }

    /**
     * The entire logic of a hill cipher
     * @param text to process
     * @param cryptVariant enum contains enumerated types like ENCRYPT or DECRYPT
     */

    private void cipherProcessing(String text, CryptVariant cryptVariant) throws MatrixException {
        text = textProcessing(text);
        Matrix<Integer> A =  new Matrix<>(getEncryptMatrix());

        if (cryptVariant.equals(CryptVariant.DECRYPT))
            A = inverseKeyMatrix(A);

        text = hasOddOrEvenLength(text);
        int fixedInputTextLength = text.length();

        Integer[] vectorA = new Integer[A.getRow()];
        List<Integer> resultOfMultiplication = new ArrayList<>();

        while (ITERATOR < fixedInputTextLength) {
            fillVectorWithData(text, vectorA);

            Matrix<Integer> B = new Matrix<>(new Integer[][] {vectorA}).transpose();
            B = A.multiply(B);
            B = B.transpose();
            B = convertDoubleDataToInteger(B);
            Arrays.stream(B.getData()[0])
                    .map(o -> o)
                    .collect(Collectors.toCollection(()  -> resultOfMultiplication));

        }

        List<Integer> modularDividedInts = modDivide(resultOfMultiplication);

        StringBuilder stringBuilder = new StringBuilder();
        modularDividedInts.stream()
                .map(integer -> ASCII_TABLE[integer])
                .forEach(stringBuilder::append);

        setProcessedText(stringBuilder.toString());
        resetIterator();
    }
    
    /**
     * Checking parity or odd string length
     * 
     * @param text to check
     * @return expanded string if length is even
     */

    private String hasOddOrEvenLength(String text) {
        return text.length()%2 == 0 ? text : text.concat(" ");
    }

    /**
     * Fill vector with given chars from text
     *
     * @param text plaintext to processing
     * @param vectorA is a vector to fill
     */
    private void fillVectorWithData(final String text, Integer[] vectorA) {
        for (int vectorIterator = 0; vectorIterator < vectorA.length; vectorIterator++) {
            for (int i = 0; i < ASCII_TABLE.length; i++) {
                if (text.charAt(ITERATOR) == ASCII_TABLE[i]) {
                    vectorA[vectorIterator] = i;
                    break;
                }
                else if (text.charAt(ITERATOR) == ' ') {
                    vectorA[vectorIterator] = (int) SPECIAL_SIGN;
                    break;
                }
            }
            ITERATOR++;
        }
    }

    /**
     * To find K-1 we have to use a bit of maths. It turns out that K-1 above can be calculated from our
     * key. A lengthy discussion will not be included here, but we will give a short example. The important
     * things to know are inverses (mod m), determinants of matrices, and matrix adjugate.
     *
     * @param matrix key matrix
     * @return inverted key matrix
     * @throws MatrixException if problem occurs with conversion
     */

    private Matrix inverseKeyMatrix(Matrix matrix) throws MatrixException {
        double determinantValue = matrix.determinant(matrix);
        double calculatedModInvValue = Algorithms.modInverse((long) determinantValue, MOD_VAL);

        matrix = matrix.adjugate().modularDivide(MOD_VAL).scalarMultiply(calculatedModInvValue);
        matrix = convertDoubleDataToInteger(matrix);
        return matrix;
    }

    /**
     * Convert Double type data values into Integer type data values
     *
     * @return matrix with changed data type.
     */

    private Matrix convertDoubleDataToInteger(Matrix A) {
        Integer[][] data = new Integer[A.getRow()][A.getColumn()];
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                long  roundValue =  Math.round((Double) A.getValue(i, j));
                data[i][j] = ((int) roundValue);
            }
        }


        A.setData(data);
        return A;
        
    }

    private void resetIterator() {
        ITERATOR = 0;
    }

}
