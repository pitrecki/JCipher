package org.pitrecki.cipher.ciphtypes.transposition.railfence;

import org.pitrecki.cipher.ciphtypes.Cipher;

import java.util.ArrayList;
import java.util.List;

import static org.pitrecki.cipher.utils.CryptVariant.DECRYPT;
import static org.pitrecki.cipher.utils.CryptVariant.ENCRYPT;

/**
 * The <b>rail fence cipher </b> (also called a zigzag cipher) is a form of transposition org.pitrecki.cipher.
 * It derives its name from the way in which it is encoded.
 *
 * The railfence cipher is a very simple, easy to crack cipher. It is a transposition org.pitrecki.cipher that
 * follows a simple rule for mixing up the characters in the plaintext to form the ciphertext. The
 * railfence cipher offers essentially no communication security, and it will be shown that it can
 * be easily broken even by hand. Although weak on its own, it can be combined with other ciphers,
 * such as a substitution cipher, the combination of which is more difficult to break than either
 * cipher on it's own. Many websites claim that the rail-fence cipher is a simpler "write down the
 * columns, read along the rows" cipher. This is equivalent to using an un-keyed columnar
 * transposition cipher
 *
 * FOR MORE INFORMATION LOOK AT: <a href="http://practicalcryptography.com/ciphers/classical-era/rail-fence/">LINK</a>
 *
 * ALSO CHECK THIS <a href="https://en.wikipedia.org/wiki/Rail_fence_cipher"> WIKI </a>
 *
 * @author Piotr 'pitrecki' Nowak
 *
 * Created by Pitrecki on 2016-10-28.
 *
 */
public class RailFenceCipher extends Cipher
{
    private int railKey;

    public RailFenceCipher(int key) {
        if (key < 0)
            throw new IllegalArgumentException("Entered key cannot be negative");
        this.railKey = key;
    }


    @Override
    public void encrypt(String inputText) {
        RailFenceCipherLogic railFenceCipherLogic = new RailFenceCipherLogic(railKey, inputText.length());

        List<String> coordinates = new ArrayList<>();

        railFenceCipherLogic.calculateCoordinates(coordinates, inputText);
        railFenceCipherLogic.fillRailFenceArrayWithText(new StringBuilder(inputText), coordinates, ENCRYPT);

        String railMatrixAsString = railFenceCipherLogic.getRailArrayMatrix().toString();
        railMatrixAsString = textProcessing(railMatrixAsString).replace("NULL", "");
        setProcessedText(railMatrixAsString);
    }

    @Override
    public void decrypt(String inputText) {
        RailFenceCipherLogic railFenceCipherLogic = new RailFenceCipherLogic(railKey, inputText.length());
        List<String> coordinates = new ArrayList<>();

        railFenceCipherLogic.calculateCoordinates(coordinates, inputText);

        StringBuilder builder = new StringBuilder(inputText);
        railFenceCipherLogic.fillRailFenceArrayWithText(builder, railFenceCipherLogic.sortCoordinates(coordinates), ENCRYPT);
        //pass by reference
        railFenceCipherLogic.fillRailFenceArrayWithText(builder, coordinates, DECRYPT);
        String decrypt = builder.substring(builder.length()/2,  builder.length());

        setProcessedText(decrypt);
    }

}
