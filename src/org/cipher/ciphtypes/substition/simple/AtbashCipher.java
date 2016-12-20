package org.cipher.ciphtypes.substition.simple;

import org.cipher.utils.Variant;

/**
 * The <b>Atbash cipher </b>is a substitution cipher with a specific key where the letters of
 * the alphabet are reversed. I.e. all 'A's are replaced with 'Z's, all 'B's are replaced
 * with 'Y's, and so on. It was originally used for the Hebrew alphabet, but can be used for any
 * alphabet. The Atbash cipher offers almost no security, and can be broken very easily. Even
 * if an adversary doesn't know a piece of ciphertext has been enciphered with the Atbash cipher, they
 * can still break it by assuming it is a substitution cipher and determining the key using
 * hill-climbing. The Atbash cipher is also an Affine cipher with a=25 and b = 25, so breaking it as an
 * affine cipher also works.
 *
 * FOR MORE INFORMATATION LOOK AT <a href="http://practicalcryptography.com/ciphers/classical-era/atbash-cipher/"> LINK </a>
 *
 * @author Piotr 'pitrecki' Nowak
 * @version 0.0.1
 * Created by Pitrecki on 2016-10-26.
 * @see SimpleSubstitutionCipher
 */

public class AtbashCipher extends SimpleSubstitutionCipher
{
    public AtbashCipher() {
        super();
    }

    /**
     *
     * @param value
     * @param variant PLUS equals Encrypt; MINUS equals Decrypt
     * @return
     */

    @Override
    protected int shift(int value, Variant variant) {
        return (ASCII_TABLE.length - 1) - value;
    }
}
