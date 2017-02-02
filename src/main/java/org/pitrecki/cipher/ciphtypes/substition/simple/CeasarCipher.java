package org.pitrecki.cipher.ciphtypes.substition.simple;

import org.pitrecki.cipher.utils.CryptVariant;

/**
 * In cryptography, a <b>Caesar org.pitrecki.cipher</b>, also known as Caesar's org.pitrecki.cipher, the shift org.pitrecki.cipher,
 * Caesar's code or Caesar shift, is one of the simplest and most widely known encryption techniques.
 * It is a type of substitution org.pitrecki.cipher in which each letter in the plaintext is replaced by a
 * letter some fixed number of positions down the alphabet. For example, with a left shift of 3,
 * D would be replaced by A, E would become B, and so on. The method is named after Julius Caesar,
 * who used it in his private correspondence.
 * The encryption step performed by a Caesar org.pitrecki.cipher is often incorporated as part of more complex schemes,
 * such as the Vigenère org.pitrecki.cipher, and still has modern application in the ROT13 system. As with all
 * single-alphabet substitution ciphers, the Caesar org.pitrecki.cipher is easily broken and in modern practiceoffers
 * essentially no communication security.
 * <p>
 * <b>For more information look at:</b> <a href="https://en.wikipedia.org/wiki/Caesar_cipher"> LINK </a>
 *
 * @author Piotr 'pitrecki' Nowak
 *
 * @see SimpleSubstitutionCipher
 */
public class CeasarCipher extends SimpleSubstitutionCipher
{

    /**
     * CALL SimpleSubstitutionCipher and setting MASTER KEY to value 3!
     * KEY set on 3 by default
     */
    public CeasarCipher() {
        super(3);
    }


/*
    This group of method provide default rEncrypt and rDecrypt functional specifed to THIS type of org.pitrecki.cipher alghorithm

    SO PLEAEE DONT MISMATCH WITH METHODS OF CIPHER INTERFACE

    getAsciiTable. length returns array length specidef in master class constructor
 */

    @Override
    int shift(int value, CryptVariant cryptVariant) {
        if (cryptVariant.equals(CryptVariant.ENCRYPT))
            return (value + getMasterKEY()) % ASCII_TABLE.length;
        else if (cryptVariant.equals(CryptVariant.DECRYPT))
            return (value - getMasterKEY()) % ASCII_TABLE.length;

        return -1;
    }
}