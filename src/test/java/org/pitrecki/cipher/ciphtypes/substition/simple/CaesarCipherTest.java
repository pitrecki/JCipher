package org.pitrecki.cipher.ciphtypes.substition.simple;

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
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Created by Pitrecki on 2016-12-24.
 */
class CaesarCipherTest implements TestContainer
{
    private Cipher caser;

    @BeforeEach
    void setUp() {
        caser = new CaesarCipher();
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
    @DisplayName("Encrypt test with different length of parameters")
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
        caser.encrypt(s);
        return caser.getProcessedText();
    }

    @Test
    @DisplayName("Testing a null argument in encrypt")
    void testNullEncryptArgument() {
        assertThatThrownBy(() -> caser.encrypt(null))
                .isInstanceOf(NullPointerException.class)
                .hasNoCause();
    }

    @Test
    @DisplayName("Testing an empty argument in encrypt")
    void testEmptyEncryptArgument() {
        caser.encrypt("");

        String actual = caser.getProcessedText();
        String excepted = "";
        assertThat(actual).isEqualTo(excepted);
    }
    //endregion


    //region Decryption test section
    @Test
    @DisplayName("Decryption test with different parameters")
    void testStandardDecrypt() {
        String plainLongMessage = "wkh jrog lv exulhg lq rurqr";
        String plainShortMessage = "vwulnh qrz";
        String plainAlphabetMessage = "defghijklmnopqrstuvwxyzabc";

        final List<String> plaintextList = listInit(plainLongMessage, plainShortMessage, plainAlphabetMessage);
        final List<String> expectedList = listInit(LONG_MESSAGE, SHORT_MESSAGE, ALPHABET);

        final List<String> actualList = plaintextList.stream()
                .map(this::invokeDecryptAndReturnDecryptedText)
                .collect(Collectors.toList());


        assertThat(actualList).isEqualTo(expectedList);
    }

    private String invokeDecryptAndReturnDecryptedText(String s) {
        caser.decrypt(s);
        return caser.getProcessedText();
    }

    @Test
    @DisplayName("Testing a null argument in decrypt")
    void testNullDecryptArgumentExpectThrows() {
        assertThatThrownBy(() -> caser.decrypt(null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("Testing an empty argument in decrypt")
    void testEmptyDecryptArgument() {
        caser.decrypt("");

        String actual = caser.getProcessedText();
        String excepted = "";
        assertThat(actual).isEqualTo(excepted);;
    }

    @Test
    @DisplayName("Test of not alphabet input argument for decrypt")
    void testNotAlphabetInputDecrypt() {
        StringBuilder builder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < random.nextInt(50); i++)
            builder.append((char) random.nextInt(255));

        caser.decrypt(builder.toString());

        assertThat(caser.getProcessedText()).isNotNull();

    }
    //endregion


    @Test
    @DisplayName("Testing toString()")
    void testToString() {
        caser.encrypt(LONG_MESSAGE);
        String actual = caser.toString();

        assertThat(actual).isNotNull();
    }

    @Test
    @DisplayName("Testing of empty/null class fields")
    void testNullField() {
        SoftAssertions.assertSoftly(softly -> {
            assertThatThrownBy(() -> caser.toString())
                    .isInstanceOf(NullPointerException.class);
            assertThat(caser.getEncryptMatrix()).isNull();
            assertThat(caser.getProcessedText()).isNull();
        });
    }

}