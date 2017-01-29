package org.pitrecki.cipher.ciphtypes.substition.simple;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.pitrecki.cipher.ciphtypes.Cipher;
import org.pitrecki.cipher.utils.TestContainer;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Created by Pitrecki on 2016-12-24.
 */
class CeasarCipherTest implements TestContainer
{
    private Cipher ceaser;

    @BeforeEach
    void setUp() {
        ceaser = new CeasarCipher();
    }

    private List<String> listInit(String ... params) {
        List<String> strList = Arrays.stream(params)
                .map(s -> s.replaceAll("[\\s\\p{Punct}\\p{Digit}]", ""))
                .map(String::toUpperCase)
                .collect(Collectors.toList());
        return strList;
    }

    //region Encryption test section

    @Test
    @DisplayName("Encrypt test with diferent length of parameters")
    void testStandardEncrypt() {
        String expLongMessage = "wkh jrog lv exulhg lq rurqr";
        String expShortMessage = "vwulnh qrz";
        String expAlphabetMessage = "defghijklmnopqrstuvwxyzabc";

        final List<String> expectedList = listInit(expLongMessage, expShortMessage, expAlphabetMessage);
        final List<String> plaintextList = listInit(LONG_MESSAGE, SHORT_MESSAGE, ALPHABET);

        final List<String> actualList = plaintextList.stream()
                .map(this::invokeEncryptAndGetEncryptedText)
                .collect(Collectors.toList());


        assertThat(actualList).isEqualTo(expectedList);
    }

    private String invokeEncryptAndGetEncryptedText(String s) {
        ceaser.encrypt(s);
        return ceaser.getProcessedText();
    }

    @Test
    @DisplayName("Testing a null argument in encrypt")
    void testNullEncryptArgument() {
        assertThrows(NullPointerException.class, () -> ceaser.encrypt(null));
    }

    @Test
    @DisplayName("Testing an empty argument in encrypt")
    void testEmptyEncryptArgument() {
        ceaser.encrypt("");

        String actual = ceaser.getProcessedText();
        String excepted = "";
        assertThat(actual).isEqualTo(excepted);
    }
    //endregion


    //region Decryption test section
    @Test
    @DisplayName("Decryption test with diferent parameters")
    void testStandardDecrypt() {
        String plainLongMessage = "wkh jrog lv exulhg lq rurqr";
        String plainShortMessage = "vwulnh qrz";
        String plainAlphabetMessage = "defghijklmnopqrstuvwxyzabc";

        final List<String> plaintextList = listInit(plainLongMessage, plainShortMessage, plainAlphabetMessage);
        final List<String> expectedList = listInit(LONG_MESSAGE, SHORT_MESSAGE, ALPHABET);

        final List<String> actualList = plaintextList.stream()
                .map(this::invokeDecryptAndReturnDeryptedText)
                .collect(Collectors.toList());


        assertThat(actualList).isEqualTo(expectedList);
    }

    private String invokeDecryptAndReturnDeryptedText(String s) {
        ceaser.decrypt(s);
        return ceaser.getProcessedText();
    }

    @Test
    @DisplayName("Testing a null argument in decrypt")
    void testNullDecrypttArgumentExpectThrows() {
        Assertions.assertThatThrownBy(() -> ceaser.decrypt(null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("Testing an empty argument in decrypt")
    void testEmptyDecryptArgument() {
        ceaser.decrypt("");

        String actual = ceaser.getProcessedText();
        String excepted = "";
        assertThat(actual).isEqualTo(excepted);;
    }

    @Test
    @DisplayName("Test of not alphabet input argument for decrypt")
    void testNotAlphaberInputDecrypt() {
        StringBuilder builder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < random.nextInt(50); i++)
            builder.append((char) random.nextInt(255));

        ceaser.decrypt(builder.toString());

        assertThat(ceaser.getProcessedText()).isNotNull();

    }
    //endregion


    @Test
    @DisplayName("Testing toString()")
    void testToString() {
        ceaser.encrypt(LONG_MESSAGE);
        String actual = ceaser.toString();

        assertThat(actual).isNotNull();
    }

    @Test
    @DisplayName("Testing of empty/null class fields")
    void testNullField() {
        SoftAssertions.assertSoftly(softly -> {
            Assertions.assertThatThrownBy(() -> ceaser.toString())
                    .isInstanceOf(NullPointerException.class);
            assertThat(ceaser.getEncryptMatrix()).isNull();
            assertThat(ceaser.getProcessedText()).isNull();
        });
    }

}