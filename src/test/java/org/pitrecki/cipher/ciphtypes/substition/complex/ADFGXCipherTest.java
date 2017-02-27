package org.pitrecki.cipher.ciphtypes.substition.complex;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.pitrecki.cipher.ciphtypes.substition.complex.adfgx.ADFGXCipher;

import java.security.InvalidKeyException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Piotr 'pitrecki' Nowak
 *         Created by Pitrecki on 2017-02-20.
 */
class ADFGXCipherTest
{
    private ADFGXCipher adfgxCipher;

    @BeforeEach
    void setUp() throws InvalidKeyException {
        String transpositionKey = "CARGO";
        String key = "btalpdhozkqfvsngicuxmrewy";
        this.adfgxCipher = new ADFGXCipher(key, transpositionKey);
    }


    @Test
    @DisplayName("Test text encryption with given key and transpostion key")
    void testCorrectEncryption() {
        adfgxCipher.encrypt("Attack at once");

        String actual = adfgxCipher.getProcessedText();
        String expected = "FAXDFADDDGDGFFFAFAXXAFAFX";
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Test text decryption with given key and transposition key")
    void testCorrectDecryption() {
        adfgxCipher.decrypt("FAXDFADDDGDGFFFAFAXXAFAFX");

        String actual = adfgxCipher.getProcessedText();
        String expected = "attackatonce".toUpperCase();

        assertThat(actual).isEqualTo(expected);
    }
}