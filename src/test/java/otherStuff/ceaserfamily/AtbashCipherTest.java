package otherStuff.ceaserfamily;

import org.pitrecki.cipher.ciphtypes.substition.simple.AtbashCipher;
import org.pitrecki.cipher.ciphtypes.substition.simple.SimpleSubstitutionCipher;

/**
 * Created by Pitrecki on 2016-10-26.
 */
public class AtbashCipherTest
{
    public static void main(String[] args) {
        SimpleSubstitutionCipher atbashCipher = new AtbashCipher();
        atbashCipher.encrypt("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        String result = atbashCipher.prepareDataToPrint();
        String expectResutlt = "ZYXWVUTSRQPONMLKJIHGFEDCBA";

        System.out.println("Result: " + result);
        System.out.println("Expected: " + expectResutlt);
        System.out.println("Is equals? " +result.equals(expectResutlt));

        System.out.println("\n");

        atbashCipher.decrypt(result);
        expectResutlt =  "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        result = atbashCipher.prepareDataToPrint();
        System.out.println("Result: " + result);
        System.out.println("Expected: " + expectResutlt);
        System.out.println("Is equals? " +result.equals(expectResutlt));
    }
}
