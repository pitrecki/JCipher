package org.pitrecki.cipher.ciphtypes.substition.complex;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.pitrecki.cipher.ciphtypes.Cipher;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.pitrecki.cipher.ciphtypes.substition.complex.BaconianCipher.Variant.STANDARD;
import static org.pitrecki.cipher.utils.TestContainer.*;

/**
 * @author Piotr 'pitrecki' Nowak
 *         Created by Pitrecki on 2016-12-28.
 */
class BaconianCipherTest
{
    private Cipher baconianCipher;

    List<String> listInit(String... params) {
        return Arrays.stream(params).map(s ->
                s.replaceAll("[\\W\\p{Digit}\\p{Punct}]", "").toUpperCase()).collect(Collectors.toList());
    }

    @Test
    @DisplayName("Testing of text encryption using standard type")
    void testStandardEncryption() {
        this.baconianCipher = new BaconianCipher(STANDARD);

        List<String> plaintextList = listInit(ALPHABET, SHORT_MESSAGE, LONG_MESSAGE);
        List<String> expectedList = listInit(
/* ALPHABET*/                "AAAAAAAAABAAABAAAABBAABAAAABABAABBAAABBBABAAAABAAAABAABABABAABABBABB" +
                                      "AAABBABABBBAABBBBBAAAABAAABBAABABAABBBAABBBABAABABABBABBABABBB",
/* SHORT_MESSAGE*/                    "BAAABBAABABAAAAABAAAABAABAABAAABBAAABBABBABAA",
/* LONG_MESSAGE*/                     "BAABAAABBBAABAAAABBAABBABABABAAAABBABAAABAAABAAAABBAABBBAAAAABAAA" +
                                      "AABAAAAABBABAAAABBAAABBABBAAAAABBABABBAAABBAB"
        );

        List<String> actuaList = Stream.of(plaintextList).flatMap(strings ->
                strings.stream().map(s -> {
                    baconianCipher.encrypt(s);
                    return baconianCipher.getProcessedText();
                } )).collect(Collectors.toList());

        assertEquals(expectedList, actuaList);
    }
}