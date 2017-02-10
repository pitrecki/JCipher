package org.pitrecki.cipher.ciphtypes.substition.simple;

import org.pitrecki.cipher.utils.CryptVariant;
import org.pitrecki.cipher.utils.math.Algorithms;

/**
 * The <b>affine org.pitrecki.cipher</b> is a type of mono alphabetic family substitution, where in each letter
 * in an alphabet is mapped to its numeric equivalent, encrypted using a simple mathematical
 * function, and converted back to a letter. The formula used means that each letter encrypts
 * to one other letter, and back again, meaning the org.pitrecki.cipher is essentially a standard substitution
 * org.pitrecki.cipher with a rule governing which letter goes to which. As such, it has the weaknesses of all
 * substitution ciphers. Each letter is enciphered with the function (ax + b) mod 26, where b is
 * the magnitude of the shift.
 * <p>
 * <b>For more information look at:</b> <a href="https://en.wikipedia.org/wiki/Affine_cipher">Link</a>
 * <p>
 * <b>THIS CLASS EXTEND SimpleSubstitutionCipher</b>
 *
 * @author Piotr 'pitrecki' Nowak
 *
 * @see SimpleSubstitutionCipher
 * Created by Pitrecki on 2016-10-20.
 */
public class AffineCipher extends SimpleSubstitutionCipher
{
    /**
     * index[0] represent a
     * index[1] represent shift b in equation
     * KEY represent KEY(a, b)
     * Math.abs protect from negative values
     */

    public AffineCipher(int[] key) {
        super(new int[]{Math.abs(key[0]), Math.abs(key[1])});
    }

    public AffineCipher(int a, int b) {
        this(new int[]{a,b});
    }


    @Override
    int shift(int value, CryptVariant cryptVariant) {
        int a = getKEY()[0];
        int b = getKEY()[1];
        if (cryptVariant == CryptVariant.ENCRYPT)
            return (a*value + b) % ASCII_TABLE.length;
        else if (cryptVariant.equals(CryptVariant.DECRYPT)) {
            return (int) ((Algorithms.modInverse(a, ASCII_TABLE.length) * (value - b)) % ASCII_TABLE.length);
        }

        return -1;
    }
}
