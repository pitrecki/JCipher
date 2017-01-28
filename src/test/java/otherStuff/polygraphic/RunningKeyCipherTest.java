package otherStuff.polygraphic;

import org.pitrecki.cipher.ciphtypes.Cipher;
import org.pitrecki.cipher.ciphtypes.polygraphic.RunningKeyCipher;

/**
 * Created by Pitrecki on 2016-12-17.
 */
public class RunningKeyCipherTest
{
    public static void main(String[] args) {
        Cipher autoCipher = new RunningKeyCipher();
        autoCipher.encrypt("THE QUICK BROWN FOX JUMPS OVER THE LAZY DOG");
        autoCipher.printProccesedData();

        autoCipher.decrypt(autoCipher.getProcessedText());
        autoCipher.printProccesedData();
    }
}
