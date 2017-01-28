package otherStuff.ceaserfamily;

import org.pitrecki.cipher.ciphtypes.Cipher;
import org.pitrecki.cipher.ciphtypes.substition.complex.BaconianCipher;
import org.pitrecki.cipher.ciphtypes.substition.complex.BaconianCipher.Variant;
import org.pitrecki.cipher.utils.AsciiGenerator;

/**
 * Created by Pitrecki on 2016-11-02.
 */
public class BaconianCipherTest
{
    public static void main(String[] args) {
        Cipher baconian = new BaconianCipher(Variant.STANDARD);

        String message = "STRIKE NOW";

        StringBuilder builder = new StringBuilder();
//        for (char letter : AsciiGenerator.upperCaseAlphabetGenerator())
//            buffer.append(letter);

//        String message = buffer.toString();
        baconian.encrypt(message);
//        char[] chars = baconian.getProcessedText().toCharArray();
//        int asciArrayIterator = 0;
//        System.out.print(baconian.ASCII_TABLE[asciArrayIterator] + " ");
//        for (int i = 0; i < chars.length; i++) {
//            System.out.print(chars[i]);
//            if ((i+1)%5 == 0) {
//                try {
//                    System.out.println();
//                    asciArrayIterator++;
//                    System.out.print(baconian.ASCII_TABLE[asciArrayIterator] + " ");
//                } catch (ArrayIndexOutOfBoundsException e) {}
//            }
//        }


        String result = baconian.getProcessedText();
        StringBuilder bu = new StringBuilder();
        for (int i = 0; i < baconian.getProcessedText().length(); i+=5)
            bu.append(result.substring(i, i + 5) + " ");
        System.out.println(bu.delete(bu.length() - 1, bu.length()));
        String expected = "baaab baaba baaaa abaaa abaab aabaa abbaa abbab babaa".toUpperCase();
        System.out.println(expected+"\n");
        System.out.println("Is equals? " + expected.equals(bu.toString()));

        baconian.decrypt(expected.replaceAll("[\\W]", ""));
        baconian.printProccesedData();

        message = "itaka";

        baconian.encrypt(message);
        String answer = baconian.prepareDataToPrint();
        baconian.printProccesedData();

        baconian.decrypt(answer);
        baconian.printProccesedData();

        for (int i = 0; i < AsciiGenerator.lowerCaseAlphabetGenerator().length; i++) {
            char c = (char) ('A' + i);
            System.out.println(i + ". " + c);
        }
    }
}
