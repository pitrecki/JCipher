package otherStuff.ceaserfamily;

import org.pitrecki.cipher.ciphtypes.substition.simple.AffineCipher;
import org.pitrecki.cipher.ciphtypes.substition.simple.SimpleSubstitutionCipher;

/**
 * Created by Pitrecki on 2016-10-20.
 */
public class AffineCipherTest
{
    public static void main(String[] args) {
        SimpleSubstitutionCipher affineCipher = new AffineCipher(new int[]{7, 5});
        affineCipher.encrypt("KOT");
        System.out.println("Encrypt:" + affineCipher.prepareDataToPrint());

        String encryptMsg = affineCipher.prepareDataToPrint();
        String expectedMsg = "XZI";

        System.out.println("Is equals? " + encryptMsg.equals(expectedMsg));
    }
}
