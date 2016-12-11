package org.cipher.ciphtypes.polygraphic;

import org.cipher.ciphtypes.Cipher;

/**
 * Created by Pitrecki on 2016-12-11.
 */
public class VinegereCipher extends Cipher
{
    private final String KEY_WORD;
    //aka tabbula recta
    private final char[][] VINEGERE_TABLE;

    public VinegereCipher(String KEY_WORD) {
        this.KEY_WORD = KEY_WORD;
        this.VINEGERE_TABLE = tabulaRectaTableGenerate();
    }

    private char[][] tabulaRectaTableGenerate() {
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

    @Override
    public void encrypt(String inputText) {

    }

    @Override
    public void decrypt(String inputText) {

    }

    @Override
    public String prepareDataToPrint() {
        return null;
    }
}
