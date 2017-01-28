package otherStuff.polygraphic;

import org.pitrecki.cipher.ciphtypes.polygraphic.AutoKeyCipher;

/**
 * Created by Pitrecki on 2016-12-17.
 */
public class AutoKeyCipherTest
{
    public static void main(String[] args) {
        AutoKeyCipher autoKeyCipher = new AutoKeyCipher("FORTIFICATION");
        autoKeyCipher.encrypt("DEFENDTHEEASTWALLOFTHECASTLE");
        String expectedAnswer = "ISWXVIBJEXIGGZEQPBIMOIGAKMHE";
        System.out.println(autoKeyCipher.toString());
        System.out.println("Equals? " + expectedAnswer.equals(autoKeyCipher.getProcessedText()));
        autoKeyCipher.decrypt(expectedAnswer);
        autoKeyCipher.printProccesedData();
        System.out.println("Equals? " + "DEFENDTHEEASTWALLOFTHECASTLE".equals(autoKeyCipher.getProcessedText()));

        String str = "FORTIFICATION";

        System.out.println(new StringBuffer(str).append(str).append(str).reverse());
}
}
