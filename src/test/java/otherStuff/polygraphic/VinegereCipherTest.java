package otherStuff.polygraphic;

import org.pitrecki.cipher.ciphtypes.Cipher;
import org.pitrecki.cipher.ciphtypes.polygraphic.VinegereCipher;

/**
 * Created by Pitrecki on 2016-12-12.
 */
public class VinegereCipherTest
{
    public static void main(String[] args) {
        Cipher vc = new VinegereCipher("lemon");
        vc.encrypt("ATTACKATDAWN");

        String expectedResult = "LXFOPVEFRNHR";
        vc.printProccesedData();
        System.out.println(vc.getProcessedText().equals(expectedResult));

        vc.decrypt(expectedResult);
        vc.printProccesedData();
        String frequqncy = vc.frequencyTextAnalysis(vc.getProcessedText());
        System.out.print(frequqncy);
    }
}
