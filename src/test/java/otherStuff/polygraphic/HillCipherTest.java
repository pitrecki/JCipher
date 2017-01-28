package otherStuff.polygraphic;

import org.pitrecki.cipher.ciphtypes.Cipher;
import org.pitrecki.cipher.ciphtypes.polygraphic.HillCipher;

/**
 * Created by Pitrecki on 2016-11-10.
 */
public class HillCipherTest
{
    public static void main(String[] args) {
        Integer[][] key = {{5, 17}, {4, 15}};
        String textToEncrypt = "dupa123n  12n";
        String expectedText;
        try {
            Cipher hill = new HillCipher(key);
            hill.encrypt(textToEncrypt);
            expectedText = hill.prepareDataToPrint();
            hill.printProccesedData();
            hill.decrypt(expectedText);
            hill.printProccesedData();
            System.out.println(hill.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Cipher hillCiph = null;
        hillCiph = new HillCipher(2);
        hillCiph.encrypt(textToEncrypt);
        hillCiph.decrypt(hillCiph.getProcessedText());
        System.out.println(hillCiph.toString());

//        Integer[][] key = {{3, 3}, {2, 5}};
//        String str = "hiat";
//        try {
//            Cipher hill = new HillCipher(key);
//            hill.decrypt(str);
//            hill.printProccesedData();
//        } catch (InvalidKeyException e) {
//            e.printStackTrace();
//        }
    }
}
