package org.pitrecki.cipher.ciphtypes.transposition;

import org.pitrecki.cipher.ciphtypes.Cipher;
import org.pitrecki.cipher.utils.math.Matrix;

import java.util.Arrays;

/**
 * <b>Transposition cipher</b>
 *
 * In cryptography, a transposition cipher is a method of encryption by which the positions
 * held by units of plaintext (which are commonly characters or groups of characters) are
 * shifted according to a regular system, so that the ciphertext constitutes a permutation of
 * the plaintext. That is, the order of the units is changed (the plaintext is reordered).
 * Mathematically a bijective function is used on the characters' positions to rEncrypt and an
 * inverse function to rDecrypt.
 *
 * FOR MORE INFO LOOK AT <a href="https://en.wikipedia.org/wiki/Transposition_cipher"> LINK</a>
 * ALSO YOU CAN CHECK THIS <a href="http://practicalcryptography.com/ciphers/classical-era/"> ANOTHER LINK</a>
 *
 * @author Piotr 'pitrecki' Nowak
 * @version 0.5.5
 * Created by Pitrecki on 2016-10-27.
 * @see Cipher
 */
@Deprecated
public abstract class TranspositionCipher extends Cipher
{
    public TranspositionCipher() {
    }

    @Override
    protected void setProcessedText(String text) {
        super.setProcessedText(text.replaceAll("[\\[\\],* ]", ""));
    }

    @Override
    protected void setCryptMatrix(Matrix cryptMatrix) {
        Character[][]  chArray = (Character[][]) cryptMatrix.getData();
        for (int i = 0; i < chArray.length; i++)
            Arrays.fill(chArray[i], '*');

        setCryptMatrix(new Matrix(chArray));
    }

    protected abstract void cryptArrayGenerator(String strText);

    @Override
    public abstract void decrypt(String inputText);

    @Override
    public abstract void encrypt(String inputText);


}
