package org.cipher.ciphtypes.polygraphic;

import org.cipher.ciphtypes.Cipher;
import org.cipher.utils.Variant;
import org.cipher.utils.math.Algorithms;
import org.cipher.utils.math.InvalidMatrixException;
import org.cipher.utils.math.Matrix;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.util.Random;

/**
 * //todo OPIS!
 * @author Piotr 'pitrecki' Nowak
 * @version 0.0.3
 * Created by Pitrecki on 2016-11-10.
 */
public class HillCipher extends Cipher
{
    private final char LETTER_FOR_BLANK_FIELD = 'X';
    private final short NUMBER_EQAULS_BLANK_FIELD = 23;
    //Numbers 221 is Ý,only true if word lenght is ODD!
    private final short SPECIAL_SIGN = 221;

    private Integer[] procesedData;

    public HillCipher(int size) {
        super();
        keyGenerator(size);
    }

    public HillCipher(Integer[][] key) throws InvalidKeyException {
        super();
        if (key.length != key[0].length)
            throw new InvalidKeyException("Key size not matched!");
        else
            setCryptMatrix(key);

    }

    private void keyGenerator(int size) {
        setCryptMatrix(new Integer[size][size]);
        for (int i = 0; i < getCryptMatrix().length; i++) {
            for (int j = 0; j < getCryptMatrix()[i].length; j++) {
                setValueInCryptMatrix(i, j, generateValue());
            }
        }
    }

    private int generateValue() {
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

    @Override
    public void decrypt(String inputText) {
        cipherProccessing(inputText, Variant.DECRYPT);
    }

    /**
     * The entire logic of a hill cipher
     * @param text to process
     * @param eVariant enum contains enumerated types like ENCRYPT or DECRYPT
     */

    private void cipherProccessing(String text, Variant eVariant) {
        text = text.replaceAll(" ", "").toUpperCase();
        Matrix A = null;

        if (eVariant.equals(Variant.ENCRYPT))
            A = new Matrix(getCryptMatrix());
        else {
            int moduloValue = ASCII_TABLE.length;
            A = new Matrix(getCryptMatrix());
            String strDetValue = A.determinant(A).toString();
            strDetValue = strDetValue.substring(0, strDetValue.lastIndexOf("."));
            BigInteger modInverse = new BigInteger(strDetValue).modInverse(BigInteger.valueOf(moduloValue));
            double calculatedModInvValue = modInverse.doubleValue();


//            double determinantModInverted = A.determinant(A);
//            BigInteger integer = new BigInteger(String.valueOf(determinantModInverted)).modInverse(BigInteger.valueOf(ASCII_TABLE.length));
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
                        matrixBData[0][matrixBIterator] = (int) NUMBER_EQAULS_BLANK_FIELD;
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