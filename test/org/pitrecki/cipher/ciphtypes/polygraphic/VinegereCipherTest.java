package org.pitrecki.cipher.ciphtypes.polygraphic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.pitrecki.cipher.ciphtypes.Cipher;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.pitrecki.cipher.utils.TestContainer.LONG_MESSAGE;

/**
 * @author Piotr 'pitrecki' Nowak
 *         Created by Pitrecki on 2016-12-28.
 */
class VinegereCipherTest
{
    private Cipher vinegereCipher;

    @BeforeEach
    void setUp() {
        this.vinegereCipher = new VinegereCipher("IFXSKCZFOPWCODUWJYPMXJ");
    }

    @Test
    @DisplayName("Testing of correct text encryption")
    void testTextEncryption() {
        String expected = "BMBYYNCNGQQTWHXEWMGAKX";

        vinegereCipher.encrypt(LONG_MESSAGE.replaceAll("[\\W]", ""));
        String actual = vinegereCipher.getProcessedText();

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Testing of correct text decryption")
    void textTextDecryption() {
        String expected = LONG_MESSAGE;

        vinegereCipher.decrypt("BMBYYNCNGQQTWHXEWMGAKX");
        String actual = vinegereCipher.getProcessedText();

        assertEquals(expected, actual);
    }
}