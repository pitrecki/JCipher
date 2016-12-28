package org.pitrecki.cipher.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Pitrecki on 2016-12-26.
 */
class AsciiGeneratorTest
{
    @Test
    @DisplayName("Testing of correct generating an array of large letters")
    void testOfCorrectGeneratingArrayOfLargeLetters() {
        char[] acutal = AsciiGenerator.upperCaseAlphabetGenerator();
        Character[] expected = new Character[26];
        Arrays.setAll(expected, i -> (char) ('A' + i));
        assertEquals(Arrays.toString(expected), Arrays.toString(acutal));
    }

    @Test
    @DisplayName("Testing of correct generating an array of small letters")
    void testOfCorrectGeneratingArrayOfSmallLetters() {
        char[] acutal = AsciiGenerator.lowerCaseAlphabetGenerator();
        Character[] expected = new Character[26];
        Arrays.setAll(expected, i -> (char) ('a' + i));
        assertEquals(Arrays.toString(expected), Arrays.toString(acutal));
    }

    @Test
    @DisplayName("Testing of correct generating an array of both type letters")
    void testOfCorrectGeneratingArrayOfBothTypeLetters() {
        char[] acutal = AsciiGenerator.bothCaseAlphabetGenerator();
        Character[] expected = new Character[26*2];

        for (int i = 0; i < expected.length /2; i++) {
            expected[i] = ((char) ('A' + i));
            expected[i + 26] = ((char) ('a' + i));
        }

        assertEquals(Arrays.toString(expected), Arrays.toString(acutal));
    }

    @Test
    @DisplayName("Testing of generating values are not nul")
    void testOfGeneratedValuesAreNotNull() {
        assertAll("Test of values", () -> {
                assertNotNull(AsciiGenerator.bothCaseAlphabetGenerator());
                assertNotNull(AsciiGenerator.lowerCaseAlphabetGenerator());
                assertNotNull(AsciiGenerator.upperCaseAlphabetGenerator());
        });
    }
}