package org.cipher.ciphtypes.substition.complex;

import org.cipher.ciphtypes.substition.simple.SimpleSubstitutionCipher;
import org.cipher.utils.math.Matrix;

import java.security.InvalidKeyException;
import java.util.*;

/**
 * The Polybius Square is essentially identical to the simple substitution cipher,
 * except that each plaintext character is enciphered as 2 ciphertext characters.
 * It can ususally be detected if there are only 5 or 6 different characters in the
 * ciphertext. This algorithm offers very little communication security, and can be
 * easily broken even by hand, especially as the messages become longer (more than
 * several hundred ciphertext characters).
 *
 * @author Piotr 'pitrecki' Nowak
 * @version 0.0.4
 * @see SimpleSubstitutionCipher
 * Created by Pitrecki on 2016-10-30.
 */
public class PolybiusSquareCipher extends ComplexSubstitutionCipher
{
    enum Encoding {ALPHABETICAL, NUMERICAL}

    //Length ====> 25
    private final int KEY_LENGTH = ASCII_TABLE.length - 1;

    //is true if encoding choosen to NUMERICAL, please see constructors arguments
    private String cipherKey;
    private boolean isEncodingSetToNumeric;

    /**
     * Specify in what form result will be printed
     * @param encoding ALPHABETILCAL OR NUMERICAL
     */

    public PolybiusSquareCipher(Encoding encoding) {
        super();
        keyGenerator();
        squareGenerator(getCipherKey());
        this.isEncodingSetToNumeric = setEncoding(encoding);
    }

    public PolybiusSquareCipher(String key, Encoding encoding) throws InvalidKeyException {
        super();
        if (key.length() != KEY_LENGTH)
            throw new InvalidKeyException("Invalid cipherKey length: " + key.length() + " , expected length: 25");
        else if (!isUnigue(key))
            throw new InvalidKeyException("Cinpher key is not unique");
        else
            this.cipherKey = key.toUpperCase();
        squareGenerator(getCipherKey());

        this.isEncodingSetToNumeric = setEncoding(encoding);
    }

    public String getCipherKey() {
        return cipherKey;
    }


    private void squareGenerator(String key) {
        Character[][] cryptSqaure = new Character[5][5];
        int index = 0;

        for (int i = 0; i < cryptSqaure.length; i++) {
            for (int j = 0; j < cryptSqaure[i].length; j++) {
                cryptSqaure[i][j] = key.charAt(index++);
            }
        }

        setCryptMatrix(new Matrix(cryptSqaure));

    }

    /**
     * Generate unique key
     * List should  prevent from duplicated values
     */
    private void keyGenerator() {
        Set<Character> chKeySet = new LinkedHashSet<>();
        do {
            char letter = ASCII_TABLE[new Random().nextInt(26)];
            if (letter != 'J')
                chKeySet.add(letter);
        } while (chKeySet.size() != KEY_LENGTH);

        this.cipherKey = chKeySet.toString().replaceAll("[\\[\\], ]", "");
    }

    /**
     * This method is responosbile for check is typed key by user is unique
     * @param key input key by user
     * @return true if key length is same, Set colection
     */
    private boolean isUnigue(String key) {
        Set<Character> characterSet = new HashSet<>();
        for (char letter : key.toCharArray())
            characterSet.add(letter);

        return key.length() == characterSet.size();
    }

    /**
     * Set {@see #isEncodingSetToNumeric} value
     * @param encoding enum encoding type
     * @return true of false
     */

    private boolean setEncoding(Encoding encoding) {
        return encoding.equals(Encoding.NUMERICAL);
    }

    /**
     * Translating decoded/encoded result of cipher processing to numeric or alphabetic answer
     *
     * @param textToEncode text to translate
     * @return translated text
     */

    protected String encodingTranslator(String textToEncode, Encoding encoding) {
        char[] chKeyArray = new char[5];
        for (int i = 0; i < chKeyArray.length; i++)
            chKeyArray[i] = (char) ('A' + i);
        StringBuilder builder = new StringBuilder();

        switch (encoding) {
            case ALPHABETICAL:
                for (int i = 0; i < textToEncode.length(); i++)
                    builder.append(chKeyArray[Character.getNumericValue(textToEncode.charAt(i))]);
                break;
            case NUMERICAL:
                if (textToEncode.matches(".*[0-9].*") && isEncodingSetToNumeric)
                    return textToEncode;
                else {
                    for (int i = 0; i < textToEncode.length(); i++) {
                        for (int j = 0; j < chKeyArray.length; j++) {
                            if (textToEncode.charAt(i) == chKeyArray[j])
                                builder.append(j);
                        }
                    }
                    break;
                }
        }

        return builder.toString();
    }

    @Override
    @SuppressWarnings("Work only for alphabetic plaintext")
    public void encrypt(String inputText) throws IllegalFormatException {
        if (inputText.matches(".*[0-9].*"))
            throw new IllegalArgumentException("Only letters, your input: " + inputText);
        inputText = inputText.replace(" ", "").toUpperCase();
        StringBuilder builder = new StringBuilder();
        int index = 0;

        try {
            while (true){
                for (int i = 0; i < getCryptMatrix().getData().length; i++) {
                    for (int j = 0; j < getCryptMatrix().getData()[i].length; j++) {
                        if ((getCryptMatrix().getData()[i][j]).equals(inputText.charAt(index))) {
                            builder.append(i);
                            builder.append(j);
                            index++;
                        }
                        else if (index >= inputText.length())
                            break;
                    }
                }
            }
        } catch (Exception e) {}


        String translatedString =
                isEncodingSetToNumeric ? builder.toString() : encodingTranslator(builder.toString(), Encoding.ALPHABETICAL);

        setProcessedText(translatedString);
    }

    @Override
    public void decrypt(String inpuText) {
        StringBuilder builder = new StringBuilder();
        if (inpuText.matches(".*[A-Z].*"))
            inpuText = encodingTranslator(inpuText, Encoding.NUMERICAL);
        int index = 0;
        while (!((inpuText.length() / 2) == builder.length())) {
            int x = Integer.parseInt(inpuText.substring(index, index + 1));
            index ++;
            int y = Integer.parseInt(inpuText.substring(index, index + 1));
            index ++;

            builder.append(getCryptMatrix().getData()[x][y]);
        }

        String translatedString =
                isEncodingSetToNumeric ? encodingTranslator(builder.toString(), Encoding.NUMERICAL) : builder.toString();

        setProcessedText(translatedString);
    }

    @Override
    public String prepareDataToPrint() {
        return "Text: " + getProcessedText() + " Generated cipherKey: " + getCipherKey();
    }

}
