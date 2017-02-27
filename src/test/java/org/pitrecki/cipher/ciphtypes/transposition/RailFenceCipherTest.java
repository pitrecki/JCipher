package org.pitrecki.cipher.ciphtypes.transposition;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.pitrecki.cipher.ciphtypes.Cipher;
import org.pitrecki.cipher.ciphtypes.transposition.railfence.RailFenceCipher;

import static org.assertj.core.api.Assertions.assertThat;
import static org.pitrecki.cipher.utils.TestContainer.ALPHABET;

/**
 * @author Piotr 'pitrecki' Nowak
 *         Created by Pitrecki on 2017-01-07.
 */
class RailFenceCipherTest
{
    private Cipher railFence;

    @BeforeEach
    void setUp() {
        int railKey = 3;
        this.railFence = new RailFenceCipher(railKey);
    }

    @DisplayName("Test correct text encryption")
    @Test
    void testEncryption() {
        String expected = "aeimquybdfhjlnprtvxzcgkosw".toUpperCase();

        railFence.encrypt(ALPHABET);
        String actual = railFence.getProcessedText();

       assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("Test correct text decryption")
    @Test
    void testDecryption() {
        String expected = ALPHABET.toLowerCase();

        railFence.decrypt("aeimquybdfhjlnprtvxzcgkosw");
        String actual = railFence.getProcessedText();

       assertThat(actual).isEqualTo(expected);
    }

}