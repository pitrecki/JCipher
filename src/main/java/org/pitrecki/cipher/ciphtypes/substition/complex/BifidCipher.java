package org.pitrecki.cipher.ciphtypes.substition.complex;

import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Bifid is a org.pitrecki.cipher which combines the Polybius square with transposition, and uses
 * fractionation to achieve diffusion. It was invented by Felix Delastelle. Delastelle
 * was a Frenchman who invented several ciphers including the bifid, trifid, and
 * four-square ciphers. The first presentation of the bifid appeared in the
 * French Revue du Génie civil in 1895 under the name of cryptographie nouvelle.
 *
 * @author Piotr 'pitrecki' Nowak
 *
 * @see PolybiusSquareCipher
 * Created by Pitrecki on 2017-01-04.
 */
public class BifidCipher extends PolybiusSquareCipher
{
    public BifidCipher() {
        super();
    }

    public BifidCipher(String key) throws InvalidKeyException {
        super(key);
    }

    private String checkIfInputTextContainsOnlyLetter(String text) {
        if (text.matches(".*[\\p{Digit}\\p{Punct}].*"))
            throw new IllegalArgumentException("Only letters, your input" + text);

        return textProcessing(text);
    }

    @Override
    public void encrypt(String inputText) {
        inputText = checkIfInputTextContainsOnlyLetter(inputText);


        List<Integer> rows = new ArrayList<>();
        List<Integer> columns = new ArrayList<>();
        fillListWithCoordinates(inputText, rows, columns);

        List<Integer> merged = Stream.concat(rows.stream(), columns.stream())
                .collect(Collectors.toList());

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < merged.size(); i += 2) {
            int rowVal = merged.get(i);
            int columnVal = merged.get(i + 1);
            builder.append(getValueFromEncryptMatrix(rowVal, columnVal));
        }


        setProcessedText(builder.toString());
    }

    @Override
    public void decrypt(String inputText) {
        inputText = checkIfInputTextContainsOnlyLetter(inputText);

        List<Integer> coordinates = new ArrayList<>();

        fillListWithCoordinates(inputText, coordinates, coordinates);

        List<Integer> rows = coordinates.subList(0, coordinates.size()/2);
        List<Integer> columns = coordinates.subList(coordinates.size()/2, coordinates.size());

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < inputText.length(); i++) {
            int rowVal = rows.get(i);
            int columnVal = columns.get(i);
            builder.append(getValueFromEncryptMatrix(rowVal, columnVal));
        }

        setProcessedText(builder.toString());

    }
}
