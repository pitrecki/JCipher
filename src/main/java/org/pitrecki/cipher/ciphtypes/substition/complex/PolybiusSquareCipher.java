package org.pitrecki.cipher.ciphtypes.substition.complex;

import org.pitrecki.cipher.ciphtypes.Cipher;
import org.pitrecki.cipher.utils.tools.PolybiusSquareMatrix;

import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.IllegalFormatException;
import java.util.List;

/**
 * The Polybius Square is essentially identical to the simple substitution cipher,
 * except that each plaintext character is enciphered as 2 ciphertext characters.
 * It can usually be detected if there are only 5 or 6 different characters in the
 * ciphertext. This algorithm offers very little communication security, and can be
 * easily broken even by hand, especially as the messages become longer (more than
 * several hundred ciphertext characters).
 *
 * @author Piotr 'pitrecki' Nowak
 *
 * @see Cipher
 * Created by Pitrecki on 2016-10-30.
 */
public class PolybiusSquareCipher extends Cipher
{
    private PolybiusSquareMatrix polybiusSquareMatrix;


    public PolybiusSquareCipher() {
        super();
        this.polybiusSquareMatrix = new PolybiusSquareMatrix();
        setEncryptMatrix(polybiusSquareMatrix.getPolybiusMatrix());
    }

    public PolybiusSquareCipher(String key) throws InvalidKeyException {
        super();;
        this.polybiusSquareMatrix = new PolybiusSquareMatrix(key);
        setEncryptMatrix(polybiusSquareMatrix.getPolybiusMatrix());
    }

    public String getCipherKey() {
        return polybiusSquareMatrix.getCipherKey();
    }


    @Override
    @SuppressWarnings("Work only with alphabetic plaintext")
    public void encrypt(String inputText) throws IllegalFormatException {
        if (inputText.matches(".*[0-9].*"))
            throw new IllegalArgumentException("Only letters, your input: " + inputText);

        inputText = textProcessing(inputText);
        List<Integer> coordinates = new ArrayList<>();

        fillListWithCoordinates(inputText, coordinates, coordinates);
        setProcessedText(coordinates.toString().replaceAll("[\\p{Punct}\\W]", ""));
    }

    /**
     * Search for coordinates in polybius square and fill list with XY points
     *
     * @param inputText plaintext
     * @param rows list which store y arguments
     * @param columns list which store x arguments
     */

    protected void fillListWithCoordinates(String inputText, List<Integer> rows, List<Integer> columns) {
        for (int i = 0; i < inputText.length(); i++) {
            for (int j = 0; j < getEncryptMatrix().getRow(); j++) {
                for (int k = 0; k < getEncryptMatrix().getColumn(); k++) {
                    if (getValueFromEncryptMatrix(j, k).equals(inputText.charAt(i))) {
                        rows.add(j);
                        columns.add(k);
                    }
                }
            }
        }
    }

    @Override
    public void decrypt(String inputText) {
        if (inputText.matches(".*[A-Za-z].*"))
            throw new IllegalArgumentException("Text to decrypt should constrains only letters");
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < inputText.length(); i+= 2) {
            int x = Character.getNumericValue(inputText.charAt(i));
            int y = Character.getNumericValue(inputText.charAt(i + 1));
            builder.append(getValueFromEncryptMatrix(x, y));
        }

        setProcessedText(builder.toString());
    }

    @Override
    public String prepareDataToPrint() {
        return "Text: " + getProcessedText() + " Generated cipherKey: " + getCipherKey();
    }

}
