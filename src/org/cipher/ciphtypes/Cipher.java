package org.cipher.ciphtypes;

import org.cipher.ciphtypes.substition.simple.SimpleSubstitutionCipher;
import org.cipher.interfaces.CipherInterface;
import org.cipher.interfaces.DataPrintable;
import org.cipher.utils.AsciiGenerator;
import org.cipher.utils.math.Matrix;

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
 * @version 0.5.2
 * @see org.cipher.interfaces.CipherInterface
 * @see org.cipher.interfaces.DataPrintable
 *
 * @see SimpleSubstitutionCipher
 * @see Matrix
 * Created by Pitrecki on 2016-11-04.
 */
public abstract class Cipher implements CipherInterface, DataPrintable
{
    public final char[] ASCII_TABLE;

    private String processedText;
    private Matrix cryptMatrix;

    public  Cipher() {
        this.ASCII_TABLE = AsciiGenerator.upperCaseAlphabetGenerator();
    }

    public String getProcessedText() {
        return processedText;
    }

    protected void setProcessedText(String text) {
        this.processedText = text;
    }

    public Matrix getCryptMatrix() {
        return cryptMatrix;
    }

    protected void setCryptMatrix(Matrix cryptMatrix) {
        this.cryptMatrix = cryptMatrix;
    }

    protected void setValueInCryptMatrix(int row, int column, Object object) {
        cryptMatrix.setValueInMatrix(row, column, object);
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
        String strCryptMatrix = "";
        if (getCryptMatrix() != null)
            strCryptMatrix = getCryptMatrix().toString();

        return "DECODED/ENCOED TEXT: " + getProcessedText() +
                "\nCRYPT MATRIX:\n" + strCryptMatrix +
                "STATISTICS: "  + frequencyTextAnalysis(getProcessedText());
    }
}
