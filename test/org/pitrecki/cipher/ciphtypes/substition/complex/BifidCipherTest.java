package org.pitrecki.cipher.ciphtypes.substition.complex;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pitrecki.cipher.ciphtypes.Cipher;

import java.security.InvalidKeyException;

/**
 * @author Piotr 'pitrecki' Nowak
 *         Created by Pitrecki on 2017-01-04.
 */
class BifidCipherTest
{
    private Cipher bifid;

    @BeforeEach
    void setUp() throws InvalidKeyException {
        String cipherKey = "phqgmeaylnofdxkrcvszwbuti";
        this.bifid = new BifidCipher(cipherKey);
    }

    @Test
    void testTextEncryption() {

    }

    @Test
    void testTextDecryption() {

    }
}