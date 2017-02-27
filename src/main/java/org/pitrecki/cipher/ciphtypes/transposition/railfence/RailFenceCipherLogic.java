package org.pitrecki.cipher.ciphtypes.transposition.railfence;

import org.pitrecki.cipher.interfaces.annotations.Decryption;
import org.pitrecki.cipher.interfaces.annotations.Encryption;
import org.pitrecki.cipher.utils.AlphanumericComparator;
import org.pitrecki.cipher.utils.CryptVariant;
import org.pitrecki.cipher.utils.math.Matrix;

import java.util.List;
import java.util.stream.Collectors;

import static org.pitrecki.cipher.utils.CryptVariant.ENCRYPT;

/**
 * Entire logic of Rail Fence cipher
 * @see RailFenceCipher
 *
 * @author Piotr 'pitrecki' Nowak
 *         Created by Pitrecki on 2017-02-27.
 */
class RailFenceCipherLogic
{
    private Matrix<Character> railArrayMatrix;
    private int railKey;

    public RailFenceCipherLogic(int railKey, int inputTextLength) {
        this.railKey = railKey;
        createRailFenceArrayMatrix(railKey, inputTextLength);
    }

    Matrix<Character> getRailArrayMatrix() {
        return railArrayMatrix;
    }

    /**
     * Fill the list with calculated coordinates in format [x,y],
     * where:
     *  x -> row index
     *  y -> column index
     * @param coordinates   list is passed by reference
     * @param text          only length is needed
     */

    @Encryption
    @Decryption
    void calculateCoordinates(List<String> coordinates, String text) {
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

    /**
     * Fill rail-fence array with plaintext
     *
     * @param text          text what will be use to fill array
     * @param coordinates   list with calculated coordinates
     * @param cryptVariant  ENCRYPT - fill array with entered text
     *                      DECRYPT - returns letters {@link RailFenceCipher#decrypt(String)}
     */

    @Encryption
    @Decryption
    void fillRailFenceArrayWithText(StringBuilder text, List<String> coordinates, CryptVariant cryptVariant) {
        int index = 0;
        for (String coordinate : coordinates) {
            int row = Integer.valueOf(coordinate.substring(1, coordinate.indexOf(',')));
            int column = Integer.valueOf(coordinate.substring(coordinate.indexOf(',') + 1, coordinate.length() - 1));

            if (cryptVariant == ENCRYPT)
                railArrayMatrix.setValueInMatrix(row, column, text.charAt(index++));
            else
                text.append(railArrayMatrix.getValue(row, column));
        }
        System.out.println();
    }


    /**
     * Transform list with coordinates to stream, execute sorting coordinates in
     * the natural order. And return as new list.
     *
     * @param coordinates list with coordinates
     * @return sorted list with coordinates
     */

    @Decryption
    List<String> sortCoordinates(List<String> coordinates) {
        return  coordinates.stream()
                .sorted((s1, s2) -> new AlphanumericComparator().compare(s1, s2))
                .collect(Collectors.toList());
    }

    private void createRailFenceArrayMatrix(int railKey, int inputTextLength) {
        railArrayMatrix = new Matrix<>(railKey, inputTextLength);
    }
}
