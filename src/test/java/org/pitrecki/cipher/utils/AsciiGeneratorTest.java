package org.pitrecki.cipher.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Pitrecki on 2016-12-26.
 */
class AsciiGeneratorTest
{
    private Character[] convertPrimitiveToObject(char[] chars) {
        return IntStream.range(0, chars.length)
                .mapToObj(value -> chars[value])
                .toArray(Character[]::new);
    }

    @Test
    @DisplayName("Testing of correct generating an array of large letters")
    void testOfCorrectGeneratingArrayOfLargeLetters() {
        Character[] acutal =  convertPrimitiveToObject(AsciiGenerator.upperCaseAlphabetGenerator());
        Character[] expected = new Character[26];

        Arrays.setAll(expected, i -> (char) ('A' + i));
        assertArrayEquals(expected, acutal);
    }

    @Test
    @DisplayName("Testing of correct generating an array of small letters")
    void testOfCorrectGeneratingArrayOfSmallLetters() {
        Character[] acutal =  convertPrimitiveToObject(AsciiGenerator.lowerCaseAlphabetGenerator());
        Character[] expected = new Character[26];
        Arrays.setAll(expected, i -> (char) ('a' + i));

        assertArrayEquals(expected, acutal);
    }

    @Test
    @DisplayName("Testing of correct generating an array of both type letters")
    void testOfCorrectGeneratingArrayOfBothTypeLetters() {
        Character[] acutal =  convertPrimitiveToObject(AsciiGenerator.bothCaseAlphabetGenerator());
        Character[] expected = new Character[26*2];

        for (int i = 0; i < expected.length /2; i++) {
            expected[i] = ((char) ('A' + i));
            expected[i + 26] = ((char) ('a' + i));
        }

        assertArrayEquals(expected, acutal);
    }

    @Test
    @DisplayName("Testing of generating values are not null")
    void testOfGeneratedValuesAreNotNull() {
        assertAll("Test of values", () -> {
                assertNotNull(AsciiGenerator.bothCaseAlphabetGenerator());
                assertNotNull(AsciiGenerator.lowerCaseAlphabetGenerator());
                assertNotNull(AsciiGenerator.upperCaseAlphabetGenerator());
        });
    }
}