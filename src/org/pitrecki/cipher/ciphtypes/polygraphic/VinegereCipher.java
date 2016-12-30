package org.pitrecki.cipher.ciphtypes.polygraphic;

import org.pitrecki.cipher.ciphtypes.Cipher;
import org.pitrecki.cipher.utils.CryptVariant;

import java.util.stream.Collectors;
import java.util.stream.Stream;

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
 * @version 0.5.8
 * Created by Pitrecki on 2016-12-11.
 */
public class VinegereCipher extends Cipher
{
    //aka tabula recta
    private final char[][] VINEGERE_TABLE;

    private String keyword;
    private String plaintext;

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

    void setPlaintext(String plaintext) {
        this.plaintext = plaintext;
    }

    String getPlaintext() {
        return plaintext;
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
     *  Invoke decrypt or encrypt  scenario
     *  @param cryptVariant ENCRYPTION or  DECRYPTION {@link CryptVariant}
     */

    private void cipherProcessing(CryptVariant cryptVariant) {
        keywordRepeater(plaintext.length());

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < keyword.length(); i++) {
            int keywordCurrentLetter = keyword.charAt(i);
            int inputTextCurrentLetter = plaintext.charAt(i);
            int result = 0;
            if (cryptVariant.equals(CryptVariant.ENCRYPT))
                result = calculateCoordinate(cryptVariant, keywordCurrentLetter, inputTextCurrentLetter);
            else
                result = calculateCoordinate(cryptVariant, keywordCurrentLetter, inputTextCurrentLetter);

            builder.append(ASCII_TABLE[result]);
        }

        setProcessedText(builder.toString());
    }

    /**
     * Repeats entered keyword until it matches the length of the plaintext.
     *
     * @param cryptTextLength length of plaintext
     */

     void keywordRepeater(int cryptTextLength) {
        if (cryptTextLength > keyword.length()) {
            int fixLength = cryptTextLength - keyword.length();
            String keyword = Stream.generate(() ->
                    this.keyword).limit(fixLength).collect(Collectors.joining()).substring(0, fixLength);
            this.keyword = keyword;
        }
        else if (cryptTextLength < keyword.length()) {
            keyword = keyword.substring(0, cryptTextLength);
        }
    }

    @Override
    public void encrypt(String inputText) {
        plaintext = textProcessing(inputText);
        cipherProcessing(CryptVariant.ENCRYPT);
    }

    @Override
    public void decrypt(String inputText) {
        plaintext = textProcessing(inputText);
        cipherProcessing(CryptVariant.DECRYPT);
    }

    /**
     *
     * @param cryptVariant DECRYPT or ENCRYPT
     * @param keywordLetter current letter keyowrd
     * @param textLetter current letter plaintext
     * @return computed integer value
     */

    int calculateCoordinate(CryptVariant cryptVariant, int keywordLetter, int textLetter) {
        switch (cryptVariant) {
            case ENCRYPT:
                return (keywordLetter + textLetter) % ASCII_TABLE.length;
            case DECRYPT:
                int result = (textLetter - keywordLetter) % ASCII_TABLE.length;
                if (result < 0)
                    result = ASCII_TABLE.length - Math.abs(result);
                return  result;
        }
        return 0;
    }



    @Override
    public String toString() {
        return super.toString() +
                "\nKEYWORD: " + getKeyword();
    }
}
