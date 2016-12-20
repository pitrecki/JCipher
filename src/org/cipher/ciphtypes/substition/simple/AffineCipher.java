package org.cipher.ciphtypes.substition.simple;

import org.cipher.utils.Variant;
import org.cipher.utils.math.Algorithms;

/**
 * The <b>affine cipher</b> is a type of monoalphabeticfamily substitution cipher, wherein each letter
 * in an alphabet is mapped to its numeric equivalent, encrypted using a simple mathematical
 * function, and converted back to a letter. The formula used means that each letter encrypts
 * to one other letter, and back again, meaning the cipher is essentially a standard substitution
 * cipher with a rule governing which letter goes to which. As such, it has the weaknesses of all
 * substitution ciphers. Each letter is enciphered with the function (ax + b) mod 26, where b is
 * the magnitude of the shift.
 * <p>
 * <b>For more information look at:</b> <a href="https://en.wikipedia.org/wiki/Affine_cipher">Link</a>
 * <p>
 * <b>THIS CLASS EXTEND SimpleSubstitutionCipher</b>
 *
 * @author Piotr 'pitrecki' Nowak
 * @version 0.0.1
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
    protected int shift(int value, Variant variant) {
        int a = getKEY()[0];
        int b = getKEY()[1];
        if (variant == Variant.ENCRYPT)
            return (a*value + b) % ASCII_TABLE.length;
        else if (variant.equals(Variant.DECRYPT)) {
            return (int) ((Algorithms.modInverse(a, ASCII_TABLE.length) * (value - b)) % ASCII_TABLE.length);
        }

        return -1;
    }
}
