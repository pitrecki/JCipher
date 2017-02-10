package org.pitrecki.cipher.utils;

/**
 * <b>ATTENTION!</b>
 * This method generate array which contains letter from A to Z
 * More about ASCII <a href="https://pl.wikipedia.org/wiki/ASCII"> ASCII </a>
 * <p>
 *
 * @author Piotr 'pitrecki' Nowak
 *
 * Created by Pitrecki on 2016-10-17.
 */
public final class AsciiGenerator
{
    /**
     * Protect from instantiated class
     */
    private AsciiGenerator() {
    }

    /**
     * This method generate letters from A to Z
     * @return char array, which contains letters from A to Z
     */

    public static char[] upperCaseAlphabetGenerator() {
        //size should be 26, because dec 90 in ASCII Table is Z, and dec 65 is A
        //for more info look on wikipedia
        char[] upperCase = new char[26];
        for (int i = 0; i < upperCase.length; i++)
            upperCase[i] = (char) ('A' + i);

        return upperCase;
    }

    /**
     * This method generate letters from a to z
     * @return char array, which contains letters from a to z
     */
    public static char[] lowerCaseAlphabetGenerator() {
        //size should be 26, because dec 122 in ASCII Table is z, and dec 97 is a
        //for more info look on wikipedia
        char[] lowerCase = new char[26];
        for (int i = 0; i < lowerCase.length; i++)
            lowerCase[i] = (char) ('a' + i);

        return lowerCase;
    }

    /**
     * This method generate both alphabetic array using method above
     * @return char array, which contains letters from A to Z and a to z
     */

    public static char[] bothCaseAlphabetGenerator() {
        //size 52 because this array contains both
        //this most efficiently way of copy arrays
        char[] bothCase = new char[upperCaseAlphabetGenerator().length + lowerCaseAlphabetGenerator().length];
        System.arraycopy(upperCaseAlphabetGenerator(),0,bothCase,0,bothCase.length/2);
        System.arraycopy(lowerCaseAlphabetGenerator(),0,bothCase, bothCase.length/2, bothCase.length/2);
        return bothCase;
    }
}
