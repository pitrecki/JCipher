package org.cipher.ciphtypes.substition.simple;

import org.cipher.ciphtypes.Cipher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.cipher.utils.TestContainer.ALPHABET;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Piotr 'pitrecki' Nowak
 *         Created by Pitrecki on 2016-12-28.
 */
class AtbashCipherTest
{
    private Cipher atbash;

    @BeforeEach
    void setUp() {
        this.atbash = new AtbashCipher();
    }

    @Test
    @DisplayName("Testing of correct encryption")
    void testTextEncryption() {
        String expected = new StringBuilder(ALPHABET).reverse().toString();

        atbash.encrypt(ALPHABET);
        String actual = atbash.getProcessedText();

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Testing of correct decryption")
    void textTextDecryption() {
        String expected = ALPHABET;

        String plaintext = new StringBuilder(ALPHABET).reverse().toString();
        atbash.decrypt(plaintext);
        String actual = atbash.getProcessedText();

        assertEquals(expected, actual);
    }
}