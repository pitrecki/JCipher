package org.cipher.ciphtypes.substition.complex;


import org.cipher.utils.Variant;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

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
 * {@link #generateAlphabetMap()}
 *
 * @author Piotr 'pitrecki' Nowak
 * @version 0.0.1
 * Created by Pitrecki on 2016-11-02.
 * @see ComplexSubstitutionCipher
 * @see org.cipher.ciphtypes.Cipher
 */
public class BaconianCipher extends ComplexSubstitutionCipher
{
    /**
     *
     */
    private final char CHARACTER_REPRESENT_BY_BINARY_ZERO = 'A';
    private final char CHARACTER_REPRESENT_BY_BINARY_ONE = 'B';
    private final int FIX_LENGTH = 5;

    private  Map<Character, String> alphabetMap;
    private boolean isStandardMode;


    /**
     * This constructor is responsible for craete diferent variants of Bacionaian cipher
     * @param ciphVariant STANDARD or DISTINCT
     */
    public BaconianCipher(Variant ciphVariant) {
        super();
        if (ciphVariant.equals(Variant.STANDARD))
            this.isStandardMode = true;

        generateAlphabetMap();


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
     * For DISTINCT it will genratara standard ASCI MAP with standard binary number represent from 0 to 25
     *
     */

    private void generateAlphabetMap() {

        this.alphabetMap = new HashMap<>(ASCII_TABLE.length);
        StringBuilder builder = new StringBuilder();

        for (int index = 0; index < ASCII_TABLE.length; index++) {
            char currentLetter = ASCII_TABLE[index];
            int value = index;

            //this condition is true only, when Variant = Standard
            //it generate MAP shown above
            if (isStandardMode) {
                value = value == 9 ? 8 : value;
                value = value == 21 ? 20 : value;
                if (value > 9) value--;
                if (value > 20) value -= 1;
            }

            String binaryVal = Integer.toString(value , 2);
            if (binaryVal.length() == FIX_LENGTH)
                builder.append(Integer.toString(value, 2));
            else {
                int currentLength = FIX_LENGTH - binaryVal.length();
                for (int k = 0; k < currentLength; k++) {
                    builder.append("0");
                }
                builder.append(Integer.toString(value, 2));
            }
            this.alphabetMap.put(currentLetter, builder.toString());

            //flush bufferm length is equal to FIX_LENGTH
            builder.delete(0, builder.length());
        }
    }

    /**
     * This method is responsible for encryption logic, depaatched on {@link #generateAlphabetMap()}
     * @param inputText standard plaintext input
     */
    @Override
    public void encrypt(String inputText) {
        StringBuilder builder = new StringBuilder();
        for (Character letter : inputText.toUpperCase().replace(" ", "").toCharArray())
            builder.append(alphabetMap.get(letter));

        setProcessedText(builder.toString().replace('0', CHARACTER_REPRESENT_BY_BINARY_ZERO).replace('1', CHARACTER_REPRESENT_BY_BINARY_ONE));
    }

    @Override
    public void decrypt(String inpuText) {
        inpuText = inpuText.replace(CHARACTER_REPRESENT_BY_BINARY_ZERO, '0').replace(CHARACTER_REPRESENT_BY_BINARY_ONE, '1');
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < inpuText.length(); i+= FIX_LENGTH) {
            builder.append(getKeyByValue(alphabetMap, inpuText.substring(i, (i + FIX_LENGTH))));
        }

        setProcessedText(builder.toString());
    }

    //For more info please look here: http://stackoverflow.com/questions/1383797/java-hashmap-how-to-get-key-from-value

    private <Character, String> Set<Character> getKeyByValue(Map<Character, String> map, String value) {
        return map.entrySet().stream().filter(entry -> Objects.equals(entry.getValue(), value)).map(Map.Entry::getKey).collect(Collectors.toSet());
    }
}