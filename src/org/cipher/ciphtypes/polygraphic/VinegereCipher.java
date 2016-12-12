package org.cipher.ciphtypes.polygraphic;

import org.cipher.ciphtypes.Cipher;

/**
 *
 * @author Piotr 'pitrecki' Nowak
 * @version 0.0.1
 * Created by Pitrecki on 2016-12-11.
 */
public class VinegereCipher extends Cipher
{
    //aka tabula recta
    private final char[][] VINEGERE_TABLE;

    private  String keyword;

    public VinegereCipher(String keyword) {
        this.keyword = keyword.toUpperCase();
        this.VINEGERE_TABLE = vinegereTableGenerate();
    }

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

    private void keyWordShifter(int cryptTextLength) {
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
        if (inputText.length() != keyword.length())
            keyWordShifter(inputText.replaceAll("[ ]", "").length());
    }

    @Override
    public void decrypt(String inputText) {
        if (inputText.length() != keyword.length())
            keyWordShifter(inputText.replaceAll("[ ]", "").length());
    }

    @Override
    public String prepareDataToPrint() {
        return getProcessedText();
    }
}
