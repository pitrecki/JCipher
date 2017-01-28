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
class RunningKeyCipherTest
{
    private Cipher runningKey;

    @BeforeEach
    void setUp() {
        this.runningKey = new RunningKeyCipher("IFXSKCZFOPWCODUWJYPMXJ");
    }

    @Test
    @DisplayName("Testing of correct text encryption")
    void testTextEncryption() {
        String expected = "BMBYYNCNGQQTWHXEWMGAKX";

        runningKey.encrypt(LONG_MESSAGE.replaceAll("[\\W]", ""));
        String actual = runningKey.getProcessedText();

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Testing of correct text decryption")
    void textTextDecryption() {
        String expected = LONG_MESSAGE;

        runningKey.decrypt("BMBYYNCNGQQTWHXEWMGAKX");
        String actual = runningKey.getProcessedText();

        assertEquals(expected, actual);
    }
}