package org.pitrecki.cipher.interfaces;

import org.pitrecki.cipher.ciphtypes.polygraphic.HillCipher;
import org.pitrecki.cipher.ciphtypes.substition.complex.PolybiusSquareCipher;
import org.pitrecki.cipher.utils.EncryptMatrixGenerator;

/**
 * The task of this interface is generate the appropriate encryption matrix.
 *
 * @author Piotr 'pitrecki' Nowak
 * @version 0.6.2
 *
 * @see HillCipher
 * @see PolybiusSquareCipher
 * Created by Pitrecki on 2017-01-09.
 */
public interface AbstractEncryptMatrixGenerator
{
    /**
     * Generate encrypted matrix
     *
     * @param generator pass requiment EncryptMatrixGenerator object
     */
    void generateEncryptMatrix(EncryptMatrixGenerator generator);
}
