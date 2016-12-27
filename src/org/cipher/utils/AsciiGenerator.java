package org.cipher.utils;

/**
 * <b>ATENTION!</b>
 * This method genearate array which contains letter from A to Z
 * More about ASCII <a href="https://pl.wikipedia.org/wiki/ASCII"> ASCII </a>
 * <p>
 *
 * @author Piotr 'pitrecki' Nowak
 * @version 0.5.2
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
     * @return char array, which contains letterz from A to Z
     */

    public static char[] upperCaseAlphabetGenerator() {
        //size should be 26, beacuse dec 90 in ASCII Table is Z, and dec 65 is A
        //for more info look on wikipedia
        char[] upperCase = new char[90 - 65 + 1];
        for (int i = 0; i < upperCase.length; i++)
            upperCase[i] = (char) ('A' + i);

        return upperCase;
    }

    /**
     * This method generate letters from a to z
     * @return char array, which contains letterz from a to z
     */
    public static char[] lowerCaseAlphabetGenerator() {
        //size should be 26, beacuse dec 122 in ASCII Table is z, and dec 97 is a
        //for more info look on wikipedia
        char[] lowrCase = new char[122-97+1];
        for (int i = 0; i < lowrCase.length; i++)
            lowrCase[i] = (char) ('a' + i);

        return lowrCase;
    }

    /**
     * This method generate both alphabetic array using method above
     * @return char array, which contains letterz from A to Z and a to z
     */

    public static char[] bothCaseAlphabetGenerator() {
        //size 52 beacuse this array contains both
        //this most efficienty way of copy arrays
        char[] bothCase = new char[upperCaseAlphabetGenerator().length + lowerCaseAlphabetGenerator().length];
        System.arraycopy(upperCaseAlphabetGenerator(),0,bothCase,0,bothCase.length/2);
        System.arraycopy(lowerCaseAlphabetGenerator(),0,bothCase, bothCase.length/2, bothCase.length/2);
        return bothCase;
    }
}
