package org.pitrecki.cipher.ciphtypes.substition.simple;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.pitrecki.cipher.ciphtypes.Cipher;

import static org.assertj.core.api.Assertions.assertThat;
import static org.pitrecki.cipher.utils.TestContainer.LONG_MESSAGE;

/**
 * @author Piotr 'pitrecki' Nowak
 *         Created by Pitrecki on 2016-12-28.
 */
class AffineCipherTest
{
    private Cipher affine;

    @BeforeEach
    void setUp() {
        affine = new AffineCipher(3, 5);
    }

    @Test
    @DisplayName("Testing of correct encryption")
    void testTextEncryption() {
        String expected = "karxvmodhinedrodsvevsv".toUpperCase();

        //the gold is buried in orono
        affine.encrypt(LONG_MESSAGE);
        String actual = affine.getProcessedText();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Testing of correct decryption")
    void testTextDecryption() {
        //the gold is buried in orono
        String expected = LONG_MESSAGE;

        String plaintext = "karxvmodhinedrodsvevsv";
        affine.decrypt(plaintext);
        String actual = affine.getProcessedText();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Key set to 0, should replace all with A")
    void testKeySetToZeroShouldReturnA() {
        affine = new AffineCipher(new int[]{0, 0});

        String expected = "AAAAAAAAAAAAAAAAAAAAAA";

        affine.encrypt(LONG_MESSAGE);
        String actual = affine.getProcessedText();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Key set to a= 1, b = 0 should return same message")
    void testKeySetToOneAndZero() {
        affine = new AffineCipher(1, 0);

        //the gold is buried in orono
        String expected = LONG_MESSAGE;

        String plaintext = expected;
        affine.decrypt(plaintext);
        String actual = affine.getProcessedText();

        assertThat(actual).isEqualTo(expected);
    }
}