package org.cipher.ciphtypes.substition.simple;

import org.cipher.ciphtypes.Cipher;
import org.cipher.interfaces.DataPrintable;
import org.cipher.utils.Variant;

import java.util.Arrays;

/**
 * <b>Substitution</b> of single letters separately—simple substitution—can be demonstrated
 * by writing out the alphabet in some order to represent the substitution. This is termed
 * a substitution alphabet. The cipher alphabet may be shifted or reversed (creating the
 * Caesar and Atbash ciphers, respectively) or scrambled in a more complex fashion, in
 * which case it is called a mixed alphabet or deranged alphabet. Traditionally, mixed
 * alphabets may be created by first writing out a keyword, removing repeated letters in it,
 * then writing all the remaining letters in the alphabet in the usual order.
 *
 * <b>For more information look at:</b> <a href="https://en.wikipedia.org/wiki/Substitution_cipher"> LINK </a>
 *
 * @author Piotr 'pitrecki' Nowak
 * @version 0.0.7
 * @see org.cipher.interfaces.CipherInterface
 * @see DataPrintable
 * Created by Pitrecki on 2016-10-24.
 */
public abstract class SimpleSubstitutionCipher extends Cipher
{
    // private enum sign {PLUS, MINUS}
//    private final short KEY = 3;

//    private String text;
//    private char[][] cryptArray;
//    private char[] asciiTable;

    private final int[] KEY;

    public SimpleSubstitutionCipher(int[] key) {
        this.KEY = key;
    }

    public SimpleSubstitutionCipher(int key) {
        this(new int[]{key});
    }

    /**
     * Empty constructor argument set key eqauls 0
     * requiremnt implmenetatnions in
     * @see AtbashCipher
     */
    public SimpleSubstitutionCipher() {
        this(new int[]{0});
    }

    /**
     * This method
     * @return first key from array
     * @see CeasarCipher
     * @see AtbashCipher
     */
    protected int getMasterKEY() {
        return this.KEY[0];
    }

    protected int[] getKEY() {
        return this.KEY;
    }

    @Override
    public void encrypt(String inputText) {
        textShifting(inputText, Variant.ENCRYPT);
    }

    @Override
    public void decrypt(String inpuText) {
        textShifting(inpuText,Variant.DECRYPT);
    }

    private void textShifting(String inpuText, Variant eVariant) {
        char[] strToCharArray = inpuText.toUpperCase().toCharArray();

        for (int i = 0; i < strToCharArray.length; i++) {
            for (int j = 0; j < ASCII_TABLE.length; j++) {
                if (strToCharArray[i] == ASCII_TABLE[j]) {
                    int index = shift(j, eVariant);
                    strToCharArray[i] = ASCII_TABLE[index];
                    break;
                }
            }
        }
        setProcessedText(Arrays.toString(strToCharArray));
    }


/*
    This group of method provide default encrypt and decrypt functional specifed to THIS type of cipher alghorithm

    SO PLEAEE DONT MISMATCH WITH METHODS OF CIPHER INTERFACE
 */

    /**
     *
     * @param index
     * @param eVariant ENCRYPT equals Encrypt; DECRYPT equals Decrypt
     * @return
     */

    protected abstract int shift(int index, Variant eVariant);

    @Override
    public String prepareDataToPrint() {
//        try {
//            return this.text = Arrays.toString(this.cryptArray[0]).replace("]", "").replace("[", "").replaceAll(", ", "");
//        } catch (NullPointerException e) {}
//
//        return null;

        return getProcessedText().replace("]", "").replace("[", "").replaceAll(", ", "");
    }

}
