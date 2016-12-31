package org.pitrecki.cipher.ciphtypes.polygraphic;

import org.pitrecki.cipher.ciphtypes.Cipher;
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
 * @version 0.6.0
 * Created by Pitrecki on 2016-11-10.
 */
public class HillCipher extends Cipher
{

    //Numbers 221 is Ý,only true if word lenght is ODD!
    private final short SPECIAL_SIGN = 221;
    //equals 26
    private final int MOD_VAL = ASCII_TABLE.length;

     /**
     * In this case constructor generate NxN size matrix with random values
     * @param size square matrix size
     */

    public HillCipher(int size)  {
        super();
        encryptMatrixGenerator(size);
    }

    public HillCipher(Matrix encryptMatrix) throws IllegalArgumentException {
        super();
        if (encryptMatrix.getData().length != encryptMatrix.getData()[0].length)
            throw new IllegalArgumentException("Key size not matched!");

            setEncryptMatrix(encryptMatrix);

    }

    public HillCipher(Integer[][] key) throws IllegalArgumentException {
        this(new Matrix(key));
    }

    private void encryptMatrixGenerator(int size) {
        EncryptMatrixGenerator encryptMatrixGenerator =
                new EncryptMatrixGenerator.EncryptMatrixGeneratorBuilder<>(Integer.class).withSize(size).build();

        Integer[] values = new Integer[size*size];
        for (int i = 0; i < size*size; i++)
            values[i] = generateRandomValue();

        encryptMatrixGenerator.fill(values);
        setEncryptMatrix(encryptMatrixGenerator.getGenereratedEncryptMatrix());

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
        return data.stream().map(integer -> integer%MOD_VAL).collect(Collectors.toList());
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
        Matrix A =  new Matrix(getEncryptMatrix());

        if (cryptVariant.equals(CryptVariant.DECRYPT)) {
            double determinantValue = A.determinant(A);
            double calculatedModInvValue = Algorithms.modInverse((long) determinantValue, MOD_VAL);

            A = A.adjugate().modular(MOD_VAL).scalarMultiply(calculatedModInvValue);
            A = convertDoubleDataToInteger(A);
        }

        int matrixARowNumber = A.getRow();

        text = text.length()%2 == 0 ? text : text.concat(" ");
        int fixedInputTextLength = text.length();

        Integer[][] matrixBData = new Integer[1][matrixARowNumber];
        List<Integer> procesedData = new ArrayList<>();

        int iterator = 0;
        while (iterator != fixedInputTextLength) {
            for (int matrixBIterator = 0; matrixBIterator < matrixARowNumber; matrixBIterator++) {
                for (int asciIndex = 0; asciIndex < ASCII_TABLE.length; asciIndex++) {
                    if (text.charAt(iterator) == ASCII_TABLE[asciIndex]) {
                        matrixBData[0][matrixBIterator] = asciIndex;
                        iterator++;
                        break;
                    }
                    else if (text.charAt(iterator) == ' ') {
                        matrixBData[0][matrixBIterator] = (int) SPECIAL_SIGN;
                        iterator++;
                        break;
                    }
                }
            }
            try {
                Matrix B = new Matrix(matrixBData).transpose();
                B = A.multiply(B);
                B = B.transpose();
                B = convertDoubleDataToInteger(B);
                Arrays.stream(B.getData()[0]).forEach(o -> procesedData.add((Integer) o));
            } catch (MatrixException | NumberFormatException e) {
                e.printStackTrace();
            }
        }

        List<Integer> integers = modDivide(procesedData);

        StringBuilder stringBuilder = new StringBuilder();
        integers.stream().map(integer -> ASCII_TABLE[integer]).forEach(stringBuilder::append);

        setProcessedText(stringBuilder.toString());
    }

    /**
     * Convert Double type data values into Integer type data values
     *
     * @return matrix with changed data type.
     */

    private Matrix convertDoubleDataToInteger(Matrix A) {
        Integer[][] newMatrixData = new Integer[A.getRow()][A.getColumn()];
        for (int i = 0; i < newMatrixData.length; i++) {
            for (int j = 0; j < newMatrixData[i].length; j++) {
                long  roundValue =  Math.round((Double) A.getData()[i][j]);
                newMatrixData[i][j] = ((int) roundValue);
            }
        }
        A.setData(newMatrixData);
        return A;
    }

}
