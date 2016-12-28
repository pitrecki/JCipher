package org.pitrecki.cipher.ciphtypes.polygraphic;

import org.pitrecki.cipher.ciphtypes.Cipher;
import org.pitrecki.cipher.utils.CryptVariant;

/**
 * The Vigenère cipher is a method of encrypting alphabetic text by using a series of different Caesar
 * ciphers based on the letters of a keyword. It is a simple form of polyalphabetic substitution.The
 * Vigenère (French pronunciation: ​[viʒnɛːʁ]) cipher has been reinvented many times. The method was
 * originally described by Giovan Battista Bellaso in his 1553 book La cifra del. Sig. Giovan Battista
 * Bellaso; however, the scheme was later misattributed to Blaise de Vigenère in the 19th century, and
 * is now widely known as the "Vigenère cipher". Though the cipher is easy to understand and implement,
 * for three centuries it resisted all attempts to break it; this earned it the description le chiffre
 * indéchiffrable (French for 'the indecipherable cipher'). Many people have tried to implement encryption
 * schemes that are essentially Vigenère ciphers.[3] Friedrich Kasiski was the first to publish a general
 * method of deciphering a Vigenère cipher, in 1863.
 *
 * @author Piotr 'pitrecki' Nowak
 * @version 0.5.5
 * Created by Pitrecki on 2016-12-11.
 */
public class VinegereCipher extends Cipher
{
    //aka tabula recta
    private final char[][] VINEGERE_TABLE;

    private String keyword;
    private String plainText;

    public VinegereCipher(String keyword) {
        this.keyword = keyword.toUpperCase();
        this.VINEGERE_TABLE = vinegereTableGenerate();
    }

    VinegereCipher() {
        this("");
    }

    String getKeyword() {
        return keyword;
    }

    void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    String getPlainText() {
        return plainText;
    }

    /**
     * To encrypt, a table of alphabets can be used, termed a tabula recta, Vigenère square, or Vigenère
     * table. It consists of the alphabet written out 26 times in different rows, each alphabet shifted
     * cyclically to the left compared to the previous alphabet, corresponding to the 26 possible Caesar
     * ciphers. At different points in the encryption process, the cipher uses a different alphabet from
     * one of the rows. The alphabet used at each point depends on a repeating keyword.
     *
     * @return generated Vinegere matrix
     */

    private char[][] vinegereTableGenerate() {
        //size 26x26
        char[][] vinegereTable = new char[ASCII_TABLE.length][ASCII_TABLE.length];
        int currentColumnIndex = 1;
        vinegereTable[0] = ASCII_TABLE;
        for (int i = 1; i  < vinegereTable.length; i++) {
            for (int j = 0; j < vinegereTable[0].length; j++) {
                if ((currentColumnIndex + j) < vinegereTable.length)
                    vinegereTable[i][j] = ASCII_TABLE[currentColumnIndex + j];
                else {
                    int replacedColumnIndex = Math.abs(vinegereTable[0].length - currentColumnIndex - j);
                    vinegereTable[i][j] = ASCII_TABLE[replacedColumnIndex];
                }
            }
            currentColumnIndex++;
        }
        return vinegereTable;
    }

    /**
     * Repeats entered keyword until it matches the length of the plaintext
     *
     * @param cryptTextLength length of plaintext
     */

     void keywordShifter(int cryptTextLength) {
        if (cryptTextLength > keyword.length()) {
            int index = 0;
            char[] keywordCharArray = this.keyword.toCharArray();
            StringBuilder builder = new StringBuilder(this.keyword);
            for (int i = 0; i < (cryptTextLength - keyword.length()); i++) {
                if (index == keyword.length())
                    index = 0;
                builder.append(keywordCharArray[index]);
                index++;
            }
            this.keyword = builder.toString();
        }
        else if (cryptTextLength < keyword.length()) {
            this.keyword = keyword.trim().substring(0, cryptTextLength);
        }
    }

    @Override
    public void encrypt(String inputText) {
        cipherProccessing(inputText, CryptVariant.ENCRYPT);
    }

    @Override
    public void decrypt(String inputText) {
        cipherProccessing(inputText, CryptVariant.DECRYPT);
    }

    /**
     *
     * @param text to decode or encode
     * @param cryptVariant enuma type eq. DECRYPT or ENCRYPT
     */

    private void cipherProccessing(String text, CryptVariant cryptVariant) {
        text = textProcessing(text);
        //necessary for inherit class
        this.plainText = text;
        // -----------------------------
        if (text.length() != keyword.length())
            keywordShifter(text.length());

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < keyword.length(); i++) {
            int keywordCurrentLetter = this.keyword.charAt(i);
            int inputTextCurrentLetter = text.charAt(i);
            int result = 0;
            switch (cryptVariant) {
                case ENCRYPT:
                    result = (keywordCurrentLetter + inputTextCurrentLetter) % ASCII_TABLE.length;
                    break;
                case DECRYPT:
                    result = (inputTextCurrentLetter - keywordCurrentLetter) % ASCII_TABLE.length;
                    if (result < 0)
                        result = ASCII_TABLE.length - Math.abs(result);
                    break;
            }
            builder.append(ASCII_TABLE[result]);
        }

        setProcessedText(builder.toString());
    }


    @Override
    public String toString() {
        return super.toString() +
                "\nKEYWORD: " + getKeyword();
    }
}
