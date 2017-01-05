package org.pitrecki.cipher.ciphtypes;

import org.pitrecki.cipher.ciphtypes.substition.simple.SimpleSubstitutionCipher;
import org.pitrecki.cipher.interfaces.CipherInterface;
import org.pitrecki.cipher.interfaces.DataPrintable;
import org.pitrecki.cipher.utils.AsciiGenerator;
import org.pitrecki.cipher.utils.math.Matrix;

import java.util.*;

/**
 * Ciphers are arguably the corner stone of cryptography. In general, a cipher is
 * simply just a set of steps (an algorithm) for performing both an encryption, and
 * the corresponding decryption. Despite might what seem to be a relatively simple
 * concept, ciphers play a crucial role in modern technology. Technologies involving
 * communication (including the internet, mobile phones, digital television or even ATMs)
 * rely on ciphers in order to maintain both security and privacy. Although most people
 * claim they're not familar with "cryptography", they are often familar with the concept
 * of ciphers, whether or not they are actually concious of it. Recent films such as
 * "The Da Vinci Code" and "National Treature: Book of Secrets" have plots centered around
 * cryptography and ciphers, bringing these concepts to the general public. This section
 * (quite appropriately) deals with individual ciphers and algorithms. They have been divided
 * based on their era and category (i.e. when were they used and how do they work). If you're
 * looking for a reference guide, refer to the alphabetical list to the right, otherwise
 * continue reading. In our effort to provide a practical approach to these, we have developed
 * a javascript implementation for each cipher that allows encryption and decryption of abitrary
 * text (of your choosing) using the cipher. Some history of each cipher is also included,
 * and tips on cryptanalysis are also provided.
 *
 *
 * @author Piotr 'pitrecki' Nowak
 * @version 0.6.0
 * @see CipherInterface
 * @see DataPrintable
 *
 * @see SimpleSubstitutionCipher
 * @see Matrix
 * Created by Pitrecki on 2016-11-04.
 */
public abstract class Cipher implements CipherInterface, DataPrintable
{
    public final char[] ASCII_TABLE;

    private String processedText;
    private Matrix encryptMatrix;

    public  Cipher() {
        this.ASCII_TABLE = AsciiGenerator.upperCaseAlphabetGenerator();
    }

    public String getProcessedText() {
        return processedText;
    }

    protected void setProcessedText(String text) {
        this.processedText = text;
    }

    public Matrix getEncryptMatrix() {
        return encryptMatrix;
    }

    protected void setEncryptMatrix(Matrix encryptMatrix) {
        this.encryptMatrix = encryptMatrix;
    }

    protected void setValueInEncryptMatrix(int row, int column, Object object) {
        encryptMatrix.setValueInMatrix(row, column, object);
    }

    protected Object getValueFromEncryptMatrix(int row, int column) {
        return encryptMatrix.getData()[row][column];
    }

    /**
     * This method provide basic functionality of counting appearance of each letter and percentage
     * usage of character in entire word
     *
     * @param textToAnalyze encrypted/decrypted word to analyze
     * @return formatted list
     */

    public String frequencyTextAnalysis(String textToAnalyze) {
        Map<Character, Integer> frequencyOcurencyMap = new HashMap<>(ASCII_TABLE.length);
        for (int i = 0; i < textToAnalyze.length(); i++) {
            char ch = textToAnalyze.charAt(i);
            if (!frequencyOcurencyMap.containsKey(ch))
                frequencyOcurencyMap.put(ch, 1);
            else
                frequencyOcurencyMap.put(ch, frequencyOcurencyMap.get(ch) + 1);
        }

        List<String> percentList = new ArrayList<>(frequencyOcurencyMap.size());

//        List<String> percentList = frequencyOcurencyMap.entrySet()
//                .stream()
//                .map(Map.Entry::getValue)
//                .map(value -> Double.valueOf(value) / textToAnalyze.length())
//                .map(divResult -> String.format(Locale.UK, "%.2f", divResult))
//                .collect(Collectors.toList());



        for (Integer number : frequencyOcurencyMap.values()) {
            Double divResult = Double.valueOf(number) / textToAnalyze.length();
            percentList.add(String.format(Locale.UK, "%.2f", divResult));
        }

        return frequencyOcurencyMap.toString() + "\n" + percentList.toString();
    }

    protected String textProcessing(String str) {
        return str.replaceAll("[\\W\\p{Digit}\\p{Punct}]", "").toUpperCase();
    }

    @Override
    public abstract void encrypt(String inputText);

    @Override
    public abstract void decrypt(String inputText);

    @Override
    public String prepareDataToPrint() {
        return getProcessedText();
    }

    /**
     * Fastest way to print every information about:
     * - crypted/decrypted text
     * - crypt matrix
     * - statistic of text analyze
     * @return string with whole information
     */

    @Override
    public String toString() {
        String strEncryptMatrix = "";
        if (getEncryptMatrix() != null)
            strEncryptMatrix = getEncryptMatrix().toString();

        return "DECODED/ENCOED TEXT: " + getProcessedText() +
                "\nENCRYPT MATRIX:\n" + strEncryptMatrix +
                "STATISTICS: "  + frequencyTextAnalysis(getProcessedText());
    }
}
