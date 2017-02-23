package org.pitrecki.cipher.ciphtypes.substition.complex;

import org.pitrecki.cipher.ciphtypes.Cipher;
import org.pitrecki.cipher.utils.tools.PolybiusSquareMatrix;

import java.security.InvalidKeyException;
import java.util.List;

/**
 * The ADFGX cipher was a field cipher used by the German Army during World War I.
 * It is closely related to the ADFGVX cipher. ADFGX is a fractionating transposition
 * cipher which combined a modified Polybius square with a single columnar transposition.
 * The cipher is named after the five possible letters used in the
 * ciphertext: A, D, F, G and X. These letters were chosen deliberately because they
 * sound very different from each other when transmitted via morse code. The intention
 * was to reduce the possibility of operator error.
 *
 * @author Piotr 'pitrecki' Nowak
 *         Created by Pitrecki on 2017-02-19.
 */
public class ADFGXCipher extends Cipher
{
    private String transpositionKey;

    /**
     * Create new instance of object
     *
     * @param key unique key entered by user
     * @param transpositionKey entered by user 
     * @throws InvalidKeyException
     */

    public ADFGXCipher(String key, String transpositionKey) throws InvalidKeyException {
        createPolybiusSquareMatrix(key);
        this.transpositionKey = transpositionKey;
    }

    /**
     * Create new instance of object and generate random polybius square matrix
     *
     * @param transpositionKey entered by user
     */

    public ADFGXCipher(String transpositionKey) throws InvalidKeyException {
        this(null, transpositionKey);
    }

    @Override
    public void encrypt(String inputText) {
        inputText = textProcessing(inputText);

        ADFGXCipherProcessor processor = new ADFGXCipherProcessor(transpositionKey, inputText, this);
        //fill list with converted coordinates
        List<Character> listWithCoordinates = processor.searchAndFillListWithConvertedCoordinates();
        Character[][] createdCodeTable = processor.createTwoDimArrayOfCharacters(listWithCoordinates);

        //fill created array with coordinates
        processor.fillArray(createdCodeTable, listWithCoordinates);

        createdCodeTable = processor.sort(createdCodeTable);
        String text = processor.readFromTopToBottom(createdCodeTable);

        //set text
        text = textProcessing(text);
        setProcessedText(text);

    }

    @Override
    public void decrypt(String inputText) {
        String copyOfTranspositionKey = transpositionKey;
        transpositionKey = sortTranspostionKey(transpositionKey);
        inputText = textProcessing(inputText);

        ADFGXCipherProcessor processor = new ADFGXCipherProcessor(transpositionKey, inputText, this);
        List<Character> convertedTextToList = processor.convertTextToList();
        Character[][] createdCodeTable = processor.createTwoDimArrayOfCharacters(convertedTextToList);

        //fill array
        processor.fillArray(createdCodeTable, convertedTextToList);
        //transpose data
        createdCodeTable = processor.transpose(createdCodeTable);
        //fill again
        createdCodeTable = processor.mergeTranspositionWithCodeTable(createdCodeTable);
        //sort
        createdCodeTable = processor.sort(createdCodeTable, copyOfTranspositionKey);
        //convert data in array with text from encrypt matrix
        Character[] characters = processor.convertDataInCodeTable(createdCodeTable);
        //read text
        String text = processor.readData(characters);
        //set text
        text = textProcessing(text);
        setProcessedText(text);

    }

    private String sortTranspostionKey(String transpositionKey) {
        return transpositionKey.chars()
                .sorted()
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    private void createPolybiusSquareMatrix(String key) throws InvalidKeyException {
        if (key != null && !key.isEmpty())
            setEncryptMatrix(new PolybiusSquareMatrix(key).getPolybiusMatrix());
        else
            setEncryptMatrix(new PolybiusSquareMatrix().getPolybiusMatrix());
    }


}
