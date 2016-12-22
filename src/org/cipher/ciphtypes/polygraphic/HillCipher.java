package org.cipher.ciphtypes.polygraphic;

import org.cipher.ciphtypes.Cipher;
import org.cipher.utils.Variant;
import org.cipher.utils.math.Algorithms;
import org.cipher.utils.math.InvalidMatrixException;
import org.cipher.utils.math.Matrix;

import java.security.InvalidKeyException;
import java.util.Random;

/**
 * In classical cryptography, the Hill cipher is a polygraphic substitution cipher based on linear algebra.
 * Invented by Lester S. Hill in 1929, it was the first polygraphic cipher in which it was practical (though barely)
 * to operate on more than three symbols at once. The following discussion assumes an elementary knowledge of matrices.
 *
 * @author Piotr 'pitrecki' Nowak
 * @version 0.0.3
 * Created by Pitrecki on 2016-11-10.
 */
public class HillCipher extends Cipher
{

    //Numbers 221 is Ý,only true if word lenght is ODD!
    private final short SPECIAL_SIGN = 221;

    private Integer[] procesedData;

    public HillCipher(int size) {
        super();
        cryptMatrixGenerator(size);
    }

    public HillCipher(Integer[][] key) throws InvalidKeyException {
        super();
        if (key.length != key[0].length)
            throw new InvalidKeyException("Key size not matched!");
        else
            setCryptMatrix(new Matrix(key));

    }

    private void cryptMatrixGenerator(int size) {

        setCryptMatrix(new Matrix(new Integer[size][size]));
        for (int i = 0; i < getCryptMatrix().getData().length; i++) {
            for (int j = 0; j < getCryptMatrix().getData()[i].length; j++) {
                setValueInCryptMatrix(i, j, generateRandomValue());
            }
        }
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
        cipherProccessing(inputText, Variant.ENCRYPT);
    }


    private Integer[] modDivide(Integer[] data) {
        for (int i = 0; i < data.length; i++)
            data[i] = (data[i] % 26);
        return data;
    }

    private int isEvenOrOdd(String text) {
        return (text.length()%2 == 0) ? text.length() : text.length() + 1;
    }

    /**
     *
     * @param inputText text to decrypt
     */

    @Override
    public void decrypt(String inputText) {
        cipherProccessing(inputText, Variant.DECRYPT);
    }

    /**
     * The entire logic of a hill cipher
     * @param text to process
     * @param variant enum contains enumerated types like ENCRYPT or DECRYPT
     */

    private void cipherProccessing(String text, Variant variant) {
        text = text.replaceAll(" ", "").toUpperCase();
        Matrix A = null;

        if (variant.equals(Variant.ENCRYPT))
            A = new Matrix(getCryptMatrix().getData());
        else {
            int moduloValue = ASCII_TABLE.length;
            A = new Matrix(getCryptMatrix().getData());
            double determinantValue = A.determinant(A);
            double calculatedModInvValue = Algorithms.modInverse((long) determinantValue, moduloValue);

            A = A.adjugate().modular(moduloValue).scalarMultiply(calculatedModInvValue).convertDoubleDataToInteger();

        }


        int rowNumber = A.getRow();
        int fixedInputTextLength = isEvenOrOdd(text);
        if (fixedInputTextLength > text.length())
            text = text.concat(" ");

        Integer[][] matrixBData = new Integer[1][rowNumber];
        this.procesedData = new Integer[fixedInputTextLength];

        int iterator = 0;
        int procDataIndex = 0;
        while (iterator != fixedInputTextLength) {
            for (int matrixBIterator = 0; matrixBIterator < rowNumber; matrixBIterator++) {
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

                for (int i = 0; i < B.getColumn(); i++) {
                    String str = B.getData()[0][i].toString();
                    this.procesedData[procDataIndex] = (Integer.parseInt(str.substring(0, str.lastIndexOf("."))));
                    procDataIndex++;
                }

            } catch (InvalidMatrixException | NumberFormatException e) {
                System.err.println(e);
            }
        }

        StringBuilder stringBuilder = new StringBuilder();
        this.procesedData = modDivide(procesedData);
        for (Integer aProcesedData : procesedData) {
            stringBuilder.append(ASCII_TABLE[aProcesedData]);
        }

        setProcessedText(stringBuilder.toString());
    }

    @Override
    public String prepareDataToPrint() {
        return getProcessedText();
    }
}
