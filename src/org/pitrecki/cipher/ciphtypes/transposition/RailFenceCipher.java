package org.pitrecki.cipher.ciphtypes.transposition;

import org.pitrecki.cipher.ciphtypes.Cipher;
import org.pitrecki.cipher.utils.AlphanumericComparator;
import org.pitrecki.cipher.utils.CryptVariant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.pitrecki.cipher.utils.CryptVariant.DECRYPT;
import static org.pitrecki.cipher.utils.CryptVariant.ENCRYPT;

/**
 * The <b>rail fence cipher </b> (also called a zigzag cipher) is a form of transposition cipher.
 * It derives its name from the way in which it is encoded.
 *
 * The railfence cipher is a very simple, easy to crack cipher. It is a transposition cipher that
 * follows a simple rule for mixing up the characters in the plaintext to form the ciphertext. The
 * railfence cipher offers essentially no communication security, and it will be shown that it can
 * be easily broken even by hand. Although weak on its own, it can be combined with other ciphers,
 * such as a substitution cipher, the combination of which is more difficult to break than either
 * cipher on it's own. Many websites claim that the rail-fence cipher is a simpler "write down the
 * columns, read along the rows" cipher. This is equivalent to using an un-keyed columnar
 * transposition cipher
 *
 * FOR MORE INFORMATION LOOK AT: <a href="http://practicalcryptography.com/ciphers/classical-era/rail-fence/">LINK</a>
 *
 * ALSO CHECK THIS <a href="https://en.wikipedia.org/wiki/Rail_fence_cipher"> WIKI </a>
 *
 * @author Piotr 'pitrecki' Nowak
 * @version 0.6.2
 * Created by Pitrecki on 2016-10-28.
 *
 */
public class RailFenceCipher extends Cipher
{
    //Numbers 221 equals Ý
    private final char SPECIAL_SIGN = 221;

    private int railKey;
    private char[][] railArray;

    public RailFenceCipher(int key) {
        if (key < 0)
            throw new IllegalArgumentException("Entered key cannot be negative");
        this.railKey = key;
    }

    /**
     * In the rail fence cipher, the plaintext is written downwards and diagonally on successive
     * "rails" of an imaginary fence, then moving up when we reach the bottom rail. When we reach
     * the top rail, the message is written downwards again until the whole plaintext is written out.
     * The message is then read off in rows. For example, if we have 3 "rails" and a message
     * of 'WE ARE DISCOVERED. FLEE AT ONCE', the cipherer writes out:
     *
     * EXAMPLE:
     *
     *    W . . . E . . . C . . . R . . . L . . . T . . . E
     *   . E . R . D . S . O . E . E . F . E . A . O . C .
     *    . . A . . . I . . . V . . . D . . . E . . . N . .
     *
     *
     * @param text  entered text
     */

    private void generateRailFenceArray(String text) {
        railArray = new char[railKey][text.length()];
        Arrays.stream(railArray)
                .forEach(chars -> Arrays.fill(chars, SPECIAL_SIGN));
    }

    /**
     * Fill the list with calculeted coordinates in format [x,y],
     * where:
     *  x -> row index
     *  y -> column index
     * @param coordinates   list is passed by reference
     * @param text          only length is needed
     */

    private void calculateCoordinates(List<String> coordinates, String text) {
        int rowIndex = 0;
        int sign = 1;
        for (int i = 0; i < text.length(); i++) {
            coordinates.add("[" + rowIndex + "," + i + "]");
            rowIndex += sign;
            if (rowIndex == railKey || rowIndex < 0) {
                sign = -sign;
                rowIndex += sign * 2;
            }
        }
    }

    @Override
    public void encrypt(String inputText) {
        generateRailFenceArray(inputText);
        List<String> coordinates = new ArrayList<>();
        calculateCoordinates(coordinates, inputText);


        fillRailFenceArrayWithText(new StringBuilder(inputText), coordinates, ENCRYPT);
        StringBuilder builder = new StringBuilder();
        Arrays.stream(railArray)
                .forEach(builder::append);

        setProcessedText(builder.toString().replaceAll("[Ý]", ""));
    }

    @Override
    public void decrypt(String inputText) {
        generateRailFenceArray(inputText);
        List<String> coordinates = new ArrayList<>();
        calculateCoordinates(coordinates, inputText);
        /*
        *   Transform list with ooordinates to stream, execute sorting coordinates in
        *   the natural order. And return as new list.
        */
        List<String> sortedCoordinates = coordinates.stream()
                .sorted((s1, s2) -> new AlphanumericComparator().compare(s1, s2))
                .collect(Collectors.toList());

        fillRailFenceArrayWithText(new StringBuilder(inputText), sortedCoordinates, ENCRYPT);

        //pass by reference
        StringBuilder decrpytedText = new StringBuilder(inputText);
        fillRailFenceArrayWithText(decrpytedText, coordinates, DECRYPT);
        String decrypt = decrpytedText.substring(decrpytedText.length()/2,  decrpytedText.length());

        setProcessedText(decrypt);
    }

    /**
     * Fill rail-fence array with plaintext
     *
     * @param text          text what will be use to fill array
     * @param coordinates   list with calculated coordinates
     * @param cryptVariant  ENCRYPT - fill array with entered text
     *                      DECRYPT - returns letters {@link #decrypt(String)}
     */


    private void fillRailFenceArrayWithText(StringBuilder text, List<String> coordinates, CryptVariant cryptVariant) {
        int index = 0;
        for (String coordinate : coordinates) {
            int row = Integer.valueOf(coordinate.substring(1, coordinate.indexOf(',')));
            int column = Integer.valueOf(coordinate.substring(coordinate.indexOf(',') + 1, coordinate.length() - 1));

            if (cryptVariant == ENCRYPT)
                railArray[row][column] = text.charAt(index++);
            else
                text.append(railArray[row][column]);
        }
    }
}
