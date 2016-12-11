package org.cipher.utils;

import org.cipher.ciphtypes.polygraphic.HillCipher;
import org.cipher.ciphtypes.substition.complex.BaconianCipher;
import org.cipher.ciphtypes.substition.simple.SimpleSubstitutionCipher;

/**
 * Simple enum class contains variant types
 * @author Piotr 'pitrecki' Nowak
 * @version 0.0.1
 * Created by Pitrecki on 2016-11-06.
 *
 * @see org.cipher.ciphtypes.Cipher
 * @see org.cipher.utils.math.Matrix
 */
public enum Variant
{
    //region variant type
    /**
     * @see org.cipher.ciphtypes.substition.complex.BaconianCipher
     * {@link BaconianCipher#generateAlphabetMap()}
     */
    STANDARD, DISTINCT,
    //endregion

    //region enum for decryption and encryption <SHIFT OPERATORS>
    /**
     * @see SimpleSubstitutionCipher
     * {@link SimpleSubstitutionCipher#shift(int, Variant)}
     * {@link SimpleSubstitutionCipher#textShifting(String, Variant)}
     *
     * @see HillCipher
     * {@link HillCipher#cipherProccessing(String, Variant)}
     */
    ENCRYPT, DECRYPT,
    //endregion
}
