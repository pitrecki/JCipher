package org.pitrecki.cipher.ciphtypes.substition.complex.baconian;


import org.pitrecki.cipher.ciphtypes.Cipher;
import org.pitrecki.cipher.interfaces.MapValueMapper;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * The Baconian cipher is named after its inventor, Sir Francis Bacon. The Baconian cipher is
 * a substitution cipher in which each letter is replaced by a sequence of 5 characters. In the
 * original cipher, these were sequences of 'A's and 'B's e.g. the letter 'D' was replaced by 'aaabb',
 * the letter 'O' was replaced by 'abbab' etc. This cipher offers very little communication security,
 * as it is a substitution cipher. As such all the methods used to cryptanalyse substitution ciphers
 * can be used to break Baconian ciphers. The main advantage of the cipher is that it allows hiding
 * the fact that a secret message has been sent at all.
 *
 * THERE ARE TWO DIFERENT VARIANT OF THIS CIPHER
 * PLEASE SEE FOR MORE DETAIL INFORMATION
 * {@link #generateAlphabetMap(Variant)}
 *
 * @author Piotr 'pitrecki' Nowak
 *
 * Created by Pitrecki on 2016-11-02.
 * @see Cipher
 */
public class BaconianCipher extends Cipher implements MapValueMapper<Character, String>
{
    public enum Variant {STANDARD, DISTINCT}

    private final char CHARACTER_REPRESENT_BY_BINARY_ZERO = 'A';
    private final char CHARACTER_REPRESENT_BY_BINARY_ONE = 'B';
    private final int FIX_LENGTH = 5;

    private  Map<Character, String> alphabetMap;


    /**
     * This constructor is responsible for create different variants of Bacionaian cipher
     * @param variant STANDARD or DISTINCT
     */
    public BaconianCipher(Variant variant) {
        super();
        generateAlphabetMap(variant);
    }

    /**
     * Details of represent values in MAP
     * !!!! TRUE ONLY FOR STANDARD MODE
     *
     *       a  AAAAA       g    AABBA       n  ABBAA       t    BAABA
     *       b  AAAAB       h    AABBB       o  ABBAB       u-v  BAABB
     *       c  AAABA       i-j  ABAAA       p  ABBBA       w    BABAA
     *       d  AAABB       k    ABAAB       q  ABBBB       x    BABAB
     *       e  AABAA       l    ABABA       r  BAAAA       y    BABBA
     *       f  AABAB       m    ABABB       s  BAAAB       z    BABBB
     *
     *
     * For DISTINCT it will generate standard ASCII MAP with standard binary number represent from 0 to 25
     *
     */

    private void generateAlphabetMap(Variant variant) {

        alphabetMap = new HashMap<>(ASCII_TABLE.length);
        StringBuilder builder = new StringBuilder();

        for (int index = 0; index < ASCII_TABLE.length; index++) {
            String currentLetter = String.valueOf(ASCII_TABLE[index]);
            int value = index;

            /*
                this condition is true only, when Variant = Standard
                Generate MAP shown above
            */
            if (variant.equals(Variant.STANDARD)) {
                if (currentLetter.matches("[JK-U]"))
                    value -= 1;
                else if (currentLetter.matches("[VW-Z]"))
                    value -= 2;
            }

            String binaryVal = Integer.toString(value , 2);
            /*
                If length of converted string is less than FIX_LENGTH (5) than append begin of builder with 0.
                After all append builder with binary value
                For example:
                C is 2, binary value is: 10
                We need fixed length binary represent of value, so we should fix representation of result
                A result of this block we recive:  ---> '000' and '10' ----> 00010
             */

            if (binaryVal.length() < FIX_LENGTH) {
                int currentLength = FIX_LENGTH - binaryVal.length();
                IntStream.range(0, currentLength)
                        .map(operand -> operand = 0)
                        .forEach(builder::append);
            }

            builder.append(Integer.toString(value, 2));

            alphabetMap.put(currentLetter.charAt(0), builder.toString());

            //prepare buffer for reuse
            builder.delete(0, builder.length());
        }
    }

    /**
     * This method is responsible for encryption logic, dispatched on {@link #generateAlphabetMap(Variant)}
     * @param inputText standard plaintext input
     */
    @Override
    public void encrypt(String inputText) {
        String text = textProcessing(inputText);
        StringBuilder builder = new StringBuilder();
        text.chars()
                .mapToObj(value -> (char) value)
                .map(character -> alphabetMap.get(character))
                .forEach(builder::append);

        String str = builder.toString().replace('0', CHARACTER_REPRESENT_BY_BINARY_ZERO).replace('1', CHARACTER_REPRESENT_BY_BINARY_ONE);
        setProcessedText(str);
    }

    @Override
    public void decrypt(String inputText) {
        inputText = inputText.replace(CHARACTER_REPRESENT_BY_BINARY_ZERO, '0').replace(CHARACTER_REPRESENT_BY_BINARY_ONE, '1');
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < inputText.length(); i+= FIX_LENGTH)
            builder.append(getKeyByValue(alphabetMap, inputText.substring(i, (i + FIX_LENGTH))));

        String decryptedText = textProcessing(builder.toString());

        if (alphabetMap.get('J').equals(alphabetMap.get('I')) || alphabetMap.get('U').equals(alphabetMap.get('V')))
            decryptedText = decryptedText.replaceAll("[JV]", "");

        setProcessedText(decryptedText);
    }

}
