package org.pitrecki.cipher.ciphtypes.substition.complex;

import java.security.InvalidKeyException;

import static org.pitrecki.cipher.ciphtypes.substition.complex.PolybiusSquareCipher.Encoding.ALPHABETICAL;

/**
 * Bifid is a cipher which combines the Polybius square with transposition, and uses
 * fractionation to achieve diffusion. It was invented by Felix Delastelle. Delastelle
 * was a Frenchman who invented several ciphers including the bifid, trifid, and
 * four-square ciphers. The first presentation of the bifid appeared in the
 * French Revue du Génie civil in 1895 under the name of cryptographie nouvelle.
 *
 * @author Piotr 'pitrecki' Nowak
 * @version 0.6.0
 * Created by Pitrecki on 2017-01-04.
 */
public class BifidCipher extends PolybiusSquareCipher
{
    public BifidCipher() {
        super(ALPHABETICAL);
    }

    public BifidCipher(String key) throws InvalidKeyException {
        super(key, ALPHABETICAL);
    }

    @Override
    public void encrypt(String inputText) {

    }

    @Override
    public void decrypt(String inpuText) {

    }
}
