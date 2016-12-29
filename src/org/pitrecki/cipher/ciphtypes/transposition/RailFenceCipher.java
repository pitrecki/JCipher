package org.pitrecki.cipher.ciphtypes.transposition;

import org.pitrecki.cipher.utils.math.Matrix;

import java.util.Arrays;

/**
 * The <b>rail fence cipher </b> (also called a zigzag cipher) is a form of transposition cipher.
 * It derives its name from the way in which it is encoded.
 *
 * The railfence cipher is a very simple, easy to crack cipher. It is a transposition cipher that
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
 * @version 0.5.5
 * Created by Pitrecki on 2016-10-28.
 *
 */
@Deprecated
public class RailFenceCipher extends TranspositionCipher
{
    private final int RAIL_KEY;

    public RailFenceCipher(int key) {
        super();
        RAIL_KEY = key;
    }

    /**
     * In the rail fence cipher, the plaintext is written downwards and diagonally on successive
     * "rails" of an imaginary fence, then moving up when we reach the bottom rail. When we reach
     * the top rail, the message is written downwards again until the whole plaintext is written out.
     * The message is then read off in rows. For example, if we have 3 "rails" and a message
     * of 'WE ARE DISCOVERED. FLEE AT ONCE', the cipherer writes out:
     *
     * EXAMPLE:
     *
     *    W . . . E . . . C . . . R . . . L . . . T . . . E
     *   . E . R . D . S . O . E . E . F . E . A . O . C .
     *    . . A . . . I . . . V . . . D . . . E . . . N . .
     *
     *
     * @param textToDecode is entered text
     */

    @Override
    protected void cryptArrayGenerator(String textToDecode) {
        String temp = textToDecode.replaceAll(" ", "");
        setCryptMatrix(new Matrix(new Character[RAIL_KEY][temp.length()]));

        int textCharIndex = 0;
        try {
            for (int j = 0; j < getCryptMatrix().getData()[0].length; j += RAIL_KEY + 1) {
                int row = 0;
                int column = j;
                do {
                    char letter = temp.charAt(textCharIndex);
                    setValueInCryptMatrix(row, column, letter);
                    row++;
                    column++;
                    textCharIndex++;
                } while (row < getCryptMatrix().getData().length);

                row -= 1;
                column -= 1;

                do {
                    row--;
                    column++;
                    char letter = temp.charAt(textCharIndex);
                    setValueInCryptMatrix(row, column, letter);
                    textCharIndex++;
                } while (row > 1);
            }
        } catch (ArrayIndexOutOfBoundsException | StringIndexOutOfBoundsException e) {}

    }

    @Override
    public void encrypt(String inputText) {
        cryptArrayGenerator(inputText);

        Character[][] tmpCryptMatrix = (Character[][]) getCryptMatrix().getData();
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < tmpCryptMatrix.length; i++)
            builder.append(Arrays.toString(tmpCryptMatrix[i]));

        setProcessedText(builder.toString());
    }

    @Override
    @SuppressWarnings("Not working yet")
    public void decrypt(String inputText) {
        cryptArrayGenerator(inputText);
        //TODO Poprawic implentacje, nie dziala jak nalezy
        //FIXME Napraw to wreszcie!
        Character[][] tmpCryptMatrix = ((Character[][]) getCryptMatrix().getData());
        StringBuilder builder = new StringBuilder();

//       += (2*RAIL_KEY) - 2
//        for (int i = 0; i < tmpCryptMatrix.length; i++) {
//            for (int j = i; j < tmpCryptMatrix[i].length; j ++) {
////                if (builder.length() < RAIL_KEY)
//                builder.append(tmpCryptMatrix[i][j]);
//
//            }
//        }

        for (int i = 0; i < tmpCryptMatrix[0].length; i++) {
            for (int j = 0; j < tmpCryptMatrix.length; j++) {
                builder.append(tmpCryptMatrix[j][i]);
            }
        }




        setProcessedText(builder.toString());
    }

}
