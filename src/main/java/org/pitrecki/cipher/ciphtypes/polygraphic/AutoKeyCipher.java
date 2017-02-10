package org.pitrecki.cipher.ciphtypes.polygraphic;

import org.pitrecki.cipher.utils.CryptVariant;

/**
 * The Autokey Cipher is a polyalphabetic substitution cipher. It is closely related
 * to the Vigenere cipher, but uses a different method of generating the key. It was
 * invented by Blaise de Vigenère in 1586, and is in general more secure than the
 * Vigenere cipher.
 *
 * @author Piotr 'pitrecki' Nowak
 *
 * Created by Pitrecki on 2016-11-07.
 * @see VinegereCipher
 */
public class AutoKeyCipher extends VinegereCipher
{
    public AutoKeyCipher(String keyword) {
        super(keyword);
    }

    /**
     * After decryption, encoded letter is added at the end of keyword.
     * Repeat until reach length of plaintext.
     * For better explanation, visit this site: <a href="http://crypto.interactive-maths.com/autokey-cipher.html#decrypt">LINK</a>
     */

    @Override
    public void decrypt(String plaintext) {
        setPlaintext(textProcessing(plaintext));

        StringBuilder builder = new StringBuilder();
        String keyword = getKeyword();
        for (int i = 0; i < getPlaintext().length(); i++) {
            int currentKeyWordIndex = keyword.charAt(i);
            int currentPlaintextIndex = getPlaintext().charAt(i);
            int result = calculateCoordinate(CryptVariant.DECRYPT, currentKeyWordIndex, currentPlaintextIndex);

            keyword +=  ASCII_TABLE[result];
            builder.append(ASCII_TABLE[result]);
        }

        setProcessedText(builder.toString());
    }

    /**
     * Create a new keyword as combination with keyword and n-first part of plaintext.
     * This step is necessary only, when encryption is performing.
     * @param cryptTextLength length of plaintext
     */

    @Override
    void keywordRepeater(int cryptTextLength) {
        if (cryptTextLength > getKeyword().length()) {
            StringBuilder builder = new StringBuilder();
            int fixLength = cryptTextLength - getKeyword().length();
            builder.append(getKeyword()).append(getPlaintext().substring(0, fixLength));
            setKeyword(builder.toString());
        }
        else if (cryptTextLength < getKeyword().length()) {
            setKeyword(getKeyword().substring(0, cryptTextLength));
        }
    }
}
