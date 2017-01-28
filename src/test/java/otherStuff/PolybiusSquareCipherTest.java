/*
package otherStuff;

import Cipher;
import PolybiusSquareCipher;
import PolybiusSquareCipher.Encoding;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.security.InvalidKeyException;
import java.util.EnumSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

*
 * Created by Pitrecki on 2016-12-20.


@Deprecated
class PolybiusSquareCipherTest
{
    private String[] plainTextTable;
    private String cryptKey;
    private String plainText;
    private String expectedValue;
    private Set<Encoding> encodingEnumSet;


    @BeforeEach
    void init() {
        this.plainTextTable = new String[2];
        this.plainTextTable[0] = "24122312212441011212134041431320202223410112331340412012";
        this.plainTextTable[1] = "CEBCCDBCCBCEEBABBCBCBDEAEBEDBDCACACCCDEBABBCDDBDEAEBCABC";

        this.cryptKey = "phqgiumeaylnofdxkrcvstzwb";
        this.plainText = "defend the east wall of the castle";
        this.expectedValue = plainText.replaceAll("[ ]", "").toUpperCase();
        this.encodingEnumSet = EnumSet.allOf(Encoding.class);

    }

    @Test
    @DisplayName("Encryption Testing")
    void encrypt() throws InvalidKeyException {
        String expectedResult;
        for (Encoding encoding : encodingEnumSet) {
            Cipher polybius = new PolybiusSquareCipher(cryptKey, encoding);
            polybius.encrypt(this.plainText);
            System.out.println("Current plaintext: " + this.plainText + " Encoding type: " + encoding);
            expectedResult = encoding.equals(Encoding.NUMERICAL) ? "24122312212441011212134041431320202223410112331340412012" : "CEBCCDBCCBCEEBABBCBCBDEAEBEDBDCACACCCDEBABBCDDBDEAEBCABC";
            assertEquals(expectedResult, polybius.getProcessedText());
            System.out.println(polybius.toString() + "\n");
        }
    }

    @Test
    @DisplayName("Decryption Testing")
    void decrypt() throws InvalidKeyException {
        for (Encoding encoding : encodingEnumSet) {
            Cipher polybius = new PolybiusSquareCipher(cryptKey, encoding);
            for (String strPlainText : plainTextTable) {
                System.out.println("Current plaintext: " + strPlainText + " Encoding type: " + encoding);
                polybius.decrypt(strPlainText);
                String expectedResult = !encoding.equals(Encoding.NUMERICAL) ?  expectedValue : "344344004204";
                assertEquals(expectedResult, polybius.getProcessedText());
                System.out.println(polybius.toString() + "\n");
            }

        }
    }


}
*/
