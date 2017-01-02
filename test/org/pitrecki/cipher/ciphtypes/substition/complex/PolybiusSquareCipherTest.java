package org.pitrecki.cipher.ciphtypes.substition.complex;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.pitrecki.cipher.ciphtypes.Cipher;

import java.security.InvalidKeyException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.pitrecki.cipher.ciphtypes.substition.complex.PolybiusSquareCipher.Encoding.ALPHABETICAL;
import static org.pitrecki.cipher.ciphtypes.substition.complex.PolybiusSquareCipher.Encoding.NUMERICAL;

/**
 * @author Piotr 'pitrecki' Nowak
 *         Created by Pitrecki on 2016-12-28.
 */
class PolybiusSquareCipherTest
{
    @Nested
    @DisplayName("ALPHABETIC OUTPUT")
    class TestUsingAlhpabeticEncoding
    {
        private Cipher polybiusCipher;

        @BeforeEach
        void setUp() throws InvalidKeyException {
            String cryptographyKey = "phqgiumeaylnofdxkrcvstzwb";
            this.polybiusCipher = new PolybiusSquareCipher(cryptographyKey, ALPHABETICAL);
        }

        @Test
        @DisplayName("Test correct encrypt using alphabetic input")
        void testEncryptionWithAlpabeticInput() {
            String plaintext = "defend the east wall of the castle";
            polybiusCipher.encrypt(plaintext);

            String expected = "CEBCCDBCCBCEEBABBCBCBDEAEBEDBDCACACCCDEBABBCDDBDEAEBCABC";
            String actual = polybiusCipher.getProcessedText();

            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("Test should throws exception")
        void testEncryptionUsingNumericalInput() {
            String plaintext = "11222331123123123";
            assertThrows(IllegalArgumentException.class, () -> polybiusCipher.encrypt(plaintext));
        }

        @Test
        @DisplayName("Test correct decrypt using alphabetic input")
        void testDecrpytionWithhAlphabeticalInput() {
            String plaintext = "CEBCCDBCCBCEEBABBCBCBDEAEBEDBDCACACCCDEBABBCDDBDEAEBCABC";
            polybiusCipher.decrypt(plaintext);

            String expected = "DEFENDTHEEASTWALLOFTHECASTLE";
            String actual = polybiusCipher.getProcessedText();

            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("Test correct decrypt using alphabetic input")
        void testDecrpytionWithhNumericalInput() {
            String plaintext = "24122312212441011212134041431320202223410112331340412012";
            polybiusCipher.decrypt(plaintext);

            String expected = "DEFENDTHEEASTWALLOFTHECASTLE";
            String actual = polybiusCipher.getProcessedText();

            assertEquals(expected, actual);
        }
    }

    @Nested
    @DisplayName("NUMERICAL OUTPUT")
    class TestUsingNumericalEncoding
    {
        private Cipher polybiusCipher;

        @BeforeEach
        void setUp() throws InvalidKeyException {
            String cryptographyKey = "phqgiumeaylnofdxkrcvstzwb";
            this.polybiusCipher = new PolybiusSquareCipher(cryptographyKey, NUMERICAL);
        }

        @Test
        @DisplayName("Test correct encrypt using alphabetic input")
        void testEncryptionWithAlpabeticInput() {
            String plaintext = "defend the east wall of the castle";
            polybiusCipher.encrypt(plaintext);

            String expected = "24122312212441011212134041431320202223410112331340412012";
            String actual = polybiusCipher.getProcessedText();

            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("Test correct decrypt using alphabetic input")
        void testDecrpytionWithhAlphabeticalInput() {
            String plaintext = "CEBCCDBCCBCEEBABBCBCBDEAEBEDBDCACACCCDEBABBCDDBDEAEBCABC";
            polybiusCipher.decrypt(plaintext);

            String expected = "344344004204";
            String actual = polybiusCipher.getProcessedText();

            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("Test correct decrypt using alphabetic input")
        void testDecrpytionWithhNumericalInput() {
            String plaintext = "24122312212441011212134041431320202223410112331340412012";
            polybiusCipher.decrypt(plaintext);

            String expected = "344344004204";
            String actual = polybiusCipher.getProcessedText();

            assertEquals(expected, actual);
        }
    }
}