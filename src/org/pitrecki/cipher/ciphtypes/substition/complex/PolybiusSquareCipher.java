package org.pitrecki.cipher.ciphtypes.substition.complex;

import org.pitrecki.cipher.ciphtypes.Cipher;
import org.pitrecki.cipher.utils.EncryptMatrixGenerator;

import java.security.InvalidKeyException;
import java.util.*;

/**
 * The Polybius Square is essentially identical to the simple substitution cipher,
 * except that each plaintext character is enciphered as 2 ciphertext characters.
 * It can ususally be detected if there are only 5 or 6 different characters in the
 * ciphertext. This algorithm offers very little communication security, and can be
 * easily broken even by hand, especially as the messages become longer (more than
 * several hundred ciphertext characters).
 *
 * @author Piotr 'pitrecki' Nowak
 * @version 0.6.0
 * @see Cipher
 * Created by Pitrecki on 2016-10-30.
 */
public class PolybiusSquareCipher extends Cipher
{
    //Length ====> 25
    private final int KEY_LENGTH = ASCII_TABLE.length - 1;

    private String cipherKey;


    public PolybiusSquareCipher() {
        super();
        randomKeyGenerator();
        encryptMatrixGenerator(getCipherKey());
    }

    public PolybiusSquareCipher(String key) throws InvalidKeyException {
        super();
        if (key.length() != KEY_LENGTH)
            throw new InvalidKeyException("Invalid cipherKey length: " + key.length() + " , expected length: 25");
        else if (!isUnigue(key))
            throw new InvalidKeyException("Cipher key is not unique");
        else if (key.matches("[Jj0-9]"))
            throw new InvalidKeyException("Key contains illegal character");
        else
            cipherKey = key.toUpperCase();
        encryptMatrixGenerator(getCipherKey());
    }

    public String getCipherKey() {
        return cipherKey;
    }


    /**
     * Create 5x5 cryptoghraphy Matrix and fill with enteted key
     * @param key entered by user
     */

    private void encryptMatrixGenerator(String key) {
        EncryptMatrixGenerator<Character> cmg = new EncryptMatrixGenerator.EncryptMatrixGeneratorBuilder<>(Character.class).build();

        Character[] characterKeyArray = key.chars()
                .mapToObj(value ->(char) value)
                .toArray(Character[]::new);

        cmg.fill(characterKeyArray);
        setEncryptMatrix(cmg.getGenereratedEncryptMatrix());
    }

    /**
     * Generate unique key
     * Set should  prevent from duplicated values
     */
    private void randomKeyGenerator() {
        Set<Character> chKeySet = new LinkedHashSet<>();
        Random random = new Random();
        do {
            char letter = ASCII_TABLE[random.nextInt(26)];
            if (letter != 'J')
                chKeySet.add(letter);
        } while (chKeySet.size() != KEY_LENGTH);

        cipherKey = textProcessing(chKeySet.toString());
    }

    /**
     * This method is responosbile for check is typed key by user is unique
     * @param key input key by user
     * @return true if key length is same, Set colection
     */
    private boolean isUnigue(String key) {
        Set<Character> characterSet = new HashSet<>();
        for (char letter : key.toCharArray())
            characterSet.add(letter);

        return key.length() == characterSet.size();
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
     * Search for coordinates in polybius sqaure and fill list with XY points
     *
     * @param inputText plaintext
     * @param rows list which store y arugments
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
            throw new IllegalArgumentException("Text to decrypt should constains only letters");
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
