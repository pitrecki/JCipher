package org.pitrecki.cipher.ciphtypes.substition.complex;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.pitrecki.cipher.ciphtypes.Cipher;

import java.security.InvalidKeyException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Piotr 'pitrecki' Nowak
 *         Created by Pitrecki on 2016-12-28.
 */
class PolybiusSquareCipherTest
{

    private Cipher polybiusCipher;

    @BeforeEach
    void setUp() throws InvalidKeyException {
        String cryptographyKey = "phqgiumeaylnofdxkrcvstzwb";
        this.polybiusCipher = new PolybiusSquareCipher(cryptographyKey);
    }

    @Test
    @DisplayName("Entered plaintext which contains numbers, should throws exception in encrypt method")
    void testNumeraicalInputShouldThrwonExceptionInEncryptMethod() {
        String plaintext = "11222331123123123";
        assertThrows(IllegalArgumentException.class, () -> polybiusCipher.encrypt(plaintext));
    }

    @Test
    @DisplayName("Check for correct decrypted text")
    void testCorrectDecryption() {
        String plaintext = "24122312212441011212134041431320202223410112331340412012";
        polybiusCipher.decrypt(plaintext);

        String expected = "DEFENDTHEEASTWALLOFTHECASTLE";
        String actual = polybiusCipher.getProcessedText();

        assertThat(actual).isEqualTo(expected);
    }


    @Test
    @DisplayName("Check for correct encrpyted text")
    void testCorrectEncryption() {
        String plaintext = "defend the east wall of the castle";
        polybiusCipher.encrypt(plaintext);

        String expected = "24122312212441011212134041431320202223410112331340412012";
        String actual = polybiusCipher.getProcessedText();

        assertThat(actual).isEqualTo(expected);
    }

}