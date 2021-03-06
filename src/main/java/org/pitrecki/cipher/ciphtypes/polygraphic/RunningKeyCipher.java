package org.pitrecki.cipher.ciphtypes.polygraphic;

import java.util.Arrays;
import java.util.Random;

/**
 *  The Running Key cipher has the same internal workings as the Vigenere cipher. The difference lies in
 *  how the key is chosen; the Vigenere org.pitrecki.cipher uses a short key that repeats, whereas the running key
 *  org.pitrecki.cipher uses a long key such as an excerpt from a book. This means the key does not repeat, making
 *  cryptanalysis more difficult. The org.pitrecki.cipher can still be broken though, as there are statistical patterns
 *  in both the key and the plaintext which can be exploited. If the key for the running key org.pitrecki.cipher comes
 *  from a statistically random source, then it becomes a 'one time pad' org.pitrecki.cipher. One time pads are
 *  theoretically unbreakable ciphers, because every possible decryption is equally likely.
 *
 *  Limit of autogenerated keyword is 60 to 5000
 *
 * @author Piotr 'pitrecki' Nowak
 *
 * Created by Pitrecki on 2016-12-17.
 * @see VinegereCipher
 */
public class RunningKeyCipher extends VinegereCipher
{
    private final int MAX_BOUND = 5000;

    public RunningKeyCipher() {
        super();
        setKeyword(keywordGenerator());
    }

    /**
     * For testing purpose only
     * @param keyword
     */
    @Deprecated
    RunningKeyCipher(String keyword) {
        super(keyword);
    }

    /**
     * This method generates a keyword in the range of from 60 to 5000
     * @return autogenerated keyword.
     */

    private String keywordGenerator() {
        //size between 60 and MAX_BOUND
        Random randomValue = new Random();
        Character[] keywordArray = new Character[randomValue.nextInt(MAX_BOUND)+60];
        for (int i = 0; i < keywordArray.length; i++) {
            int randomCharacter = randomValue.nextInt(ASCII_TABLE.length);
            keywordArray[i] = ASCII_TABLE[randomCharacter];
        }

        return textProcessing(Arrays.toString(keywordArray));
    }

    @Override
    public String prepareDataToPrint() {
        return "GENERATED KEYWORD: " + getKeyword()
                +"\nTEXT: " + getProcessedText();
    }
}
