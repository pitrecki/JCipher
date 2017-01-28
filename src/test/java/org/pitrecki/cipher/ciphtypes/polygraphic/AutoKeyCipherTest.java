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
class AutoKeyCipherTest
{
    private Cipher autokeyCipher;

    @BeforeEach
    void setUp() {
        String keyword = "FORTIFICATION";
        this.autokeyCipher = new AutoKeyCipher(keyword);
    }

    @Test
    @DisplayName("Testing of correct text encryption")
    void testTextEncryption() {
        String expected = "yvvzwqlksucfvxkmtccrvg".toUpperCase();

        //the gold is buried in orono
        autokeyCipher.encrypt(LONG_MESSAGE);
        String actual = autokeyCipher.getProcessedText();

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Testing of correct text decryption")
    void testTextDecryption() {
        //the gold is buried in orono
        String expected = LONG_MESSAGE;

        autokeyCipher.decrypt("yvvzwqlksucfvxkmtccrvg");
        String actual = autokeyCipher.getProcessedText();

        assertEquals(expected, actual);
    }
}