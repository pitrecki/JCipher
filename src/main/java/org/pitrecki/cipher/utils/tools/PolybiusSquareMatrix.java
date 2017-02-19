package org.pitrecki.cipher.utils.tools;

import org.pitrecki.cipher.interfaces.AbstractEncryptMatrixGenerator;
import org.pitrecki.cipher.utils.AsciiGenerator;
import org.pitrecki.cipher.utils.EncryptMatrixGenerator;
import org.pitrecki.cipher.utils.math.Matrix;

import java.security.InvalidKeyException;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

/**
 * @author Piotr 'pitrecki' Nowak
 *         Created by Pitrecki on 2017-02-19.
 */
public class PolybiusSquareMatrix implements AbstractEncryptMatrixGenerator
{
    private final char[] ASCII_TABLE = AsciiGenerator.lowerCaseAlphabetGenerator();
    //Length ====> 25
    private final int KEY_LENGTH = ASCII_TABLE.length - 1;

    private String cipherKey;
    private Matrix<Character> matrix;

    public PolybiusSquareMatrix() {
        randomKeyGenerator();
        initMatrixGenerator();
    }

    public PolybiusSquareMatrix(String key) throws InvalidKeyException{
        if (key.length() != KEY_LENGTH)
            throw new InvalidKeyException("Invalid cipher key length: " + key.length() + " , expected length: 25");
        else if (!isUnique(key))
            throw new InvalidKeyException("Cipher key is not unique");
        //todo fix regex
        else if (key.matches("[Jj0-9]"))
            throw new InvalidKeyException("Key contains illegal character");
        else
            cipherKey = key.toUpperCase();

        initMatrixGenerator();
    }

    public String getCipherKey() {
        return cipherKey;
    }

    public Matrix<Character> getPolybiusMatrix() {
        return matrix;
    }

    /**
     * Create 5x5 cryptography Matrix and fill with eenteredkey
     * @param generator entered by user
     */

    @Override
    public void generateEncryptMatrix(EncryptMatrixGenerator generator) {
        Character[] characterKeyArray = cipherKey.chars()
                .mapToObj(value ->(char) value)
                .toArray(Character[]::new);

        generator.fill(characterKeyArray);
        matrix = generator.getMatrix();
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
            if (letter != 'j')
                chKeySet.add(letter);
        } while (chKeySet.size() < KEY_LENGTH);

        cipherKey = chKeySet.toString().replaceAll("[\\p{Digit}\\p{Punct}\\s]", "").replaceAll("\\[\\]","");
    }

    /**
     * This method is responsible for check is typed key by user is unique
     * @param key input key by user
     * @return true if key length is same, Set collection
     */
    private boolean isUnique(String key) {
        Set<Character> characterSet = new HashSet<>();
        for (char letter : key.toCharArray())
            characterSet.add(letter);

        return key.length() == characterSet.size();
    }

    private void initMatrixGenerator() {
        generateEncryptMatrix(new EncryptMatrixGenerator.EncryptMatrixGeneratorBuilder<>(Character.class).build());
    }
}
