package org.pitrecki.cipher.ciphtypes.substition.complex;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pitrecki.cipher.ciphtypes.Cipher;

import java.security.InvalidKeyException;

import static org.assertj.core.api.Assertions.assertThat;

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
        String expected = "ffywaliaykhczaoeucplrlrleasr".toUpperCase();
        bifid.encrypt("defend the east wall of the castle");

        String actual = bifid.getProcessedText();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void testTextDecryption() {
        String expected = "defendtheeastwallofthecastle".toUpperCase();

        String plaintext = "ffywaliaykhczaoeucplrlrleasr";
        bifid.decrypt(plaintext);
        String actual = bifid.getProcessedText();

        assertThat(actual).isEqualTo(expected);
    }
}