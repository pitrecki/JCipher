package org.pitrecki.cipher.ciphtypes.substition.simple;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.pitrecki.cipher.ciphtypes.Cipher;

import static org.assertj.core.api.Assertions.assertThat;
import static org.pitrecki.cipher.utils.TestContainer.ALPHABET;

/**
 * @author Piotr 'pitrecki' Nowak
 *         Created by Pitrecki on 2016-12-28.
 */
class AtbashCipherTest
{
    private Cipher atbash;

    @BeforeEach
    void setUp() {
        atbash = new AtbashCipher();
    }

    @Test
    @DisplayName("Testing of correct encryption")
    void testTextEncryption() {
        String expected = new StringBuilder(ALPHABET).reverse().toString();

        atbash.encrypt(ALPHABET);
        String actual = atbash.getProcessedText();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Testing of correct decryption")
    void textTextDecryption() {
        String expected = ALPHABET;

        String plaintext = new StringBuilder(ALPHABET).reverse().toString();
        atbash.decrypt(plaintext);
        String actual = atbash.getProcessedText();

        assertThat(actual).isEqualTo(expected);
    }
}