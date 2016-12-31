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
class HillCipherTest
{
    private Cipher hillCipher;

    @BeforeEach
    void setUp() {
        Integer[][] key = {{5, 17}, {4, 15}};
        this.hillCipher = new HillCipher(key);
    }

    @Test
    @DisplayName("Encryption testing with entered key")
    void testEncryptionWithGivenKey() {
        String expected = "gzscxnvcdjzxeovcrclsrc".toUpperCase();

        hillCipher.encrypt(LONG_MESSAGE);
        String actual = hillCipher.getProcessedText();

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Decryption testing with entered key")
    void testDecryptionWithGivenKey() {
        hillCipher.decrypt("gzscxnvcdjzxeovcrclsrc");
        String actual = hillCipher.getProcessedText();

        assertEquals(LONG_MESSAGE, actual);
    }

}