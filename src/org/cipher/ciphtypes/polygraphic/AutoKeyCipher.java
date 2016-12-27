package org.cipher.ciphtypes.polygraphic;

/**
 * The Autokey Cipher is a polyalphabetic substitution cipher. It is closely related
 * to the Vigenere cipher, but uses a different method of generating the key. It was
 * invented by Blaise de Vigenère in 1586, and is in general more secure than the
 * Vigenere cipher.
 *
 * @author Piotr 'pitrecki' Nowak
 * @version 0.5.5
 * Created by Pitrecki on 2016-11-07.
 * @see VinegereCipher
 */
public class AutoKeyCipher extends VinegereCipher
{
    public AutoKeyCipher(String keyword) {
        super(keyword);
    }

    /**
     * Create a new keyword as combination with keyword and n-first part of plaintext
     *
     * @param cryptTextLength length of plaintext
     */

    @Override
    void keywordShifter(int cryptTextLength) {
        if (cryptTextLength > getKeyword().length()) {
            StringBuilder builder = new StringBuilder();
            int proccessedLength = cryptTextLength - getKeyword().length();
            builder.append(getKeyword()).append(getPlainText().trim().substring(0, proccessedLength));
            setKeyword(builder.toString());
        }
        else if (cryptTextLength < getKeyword().length()) {
            setKeyword(getKeyword().trim().substring(0, cryptTextLength));
        }
    }
}
