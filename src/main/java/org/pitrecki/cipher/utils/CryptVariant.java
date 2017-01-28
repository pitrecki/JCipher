package org.pitrecki.cipher.utils;

import org.pitrecki.cipher.ciphtypes.Cipher;
import org.pitrecki.cipher.ciphtypes.polygraphic.HillCipher;
import org.pitrecki.cipher.ciphtypes.substition.simple.SimpleSubstitutionCipher;
import org.pitrecki.cipher.utils.math.Matrix;

/**
 * Simple enum class contains variant types
 * @author Piotr 'pitrecki' Nowak
 * @version 0.6.2
 * Created by Pitrecki on 2016-11-06.
 *
 * @see Cipher
 * @see Matrix
 */
public enum CryptVariant
{
    //region enum for decryption and encryption <SHIFT OPERATORS>
    /**
     * @see SimpleSubstitutionCipher
     * {@link SimpleSubstitutionCipher#shift(int, CryptVariant)}
     * {@link SimpleSubstitutionCipher#textShifting(String, CryptVariant)}
     *
     * @see HillCipher
     * {@link HillCipher#cipherProcessing(String, CryptVariant)}
     */
    ENCRYPT, DECRYPT,
    //endregion
}
