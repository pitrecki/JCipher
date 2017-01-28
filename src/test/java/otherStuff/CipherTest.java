package otherStuff;

import org.pitrecki.cipher.ciphtypes.Cipher;
import org.pitrecki.cipher.ciphtypes.substition.simple.CeasarCipher;
import org.pitrecki.cipher.utils.file.FileOperator;

import java.nio.file.Paths;

/**
 * Created by Pitrecki on 2016-12-15.
 */
public class CipherTest
{
    public static void main(String[] args) {

        Cipher ceaser = new CeasarCipher();
        ceaser.encrypt("THE QUICK BROWN FOX JUMPS OVER THE LAZY DOG");
        String cipherText = ceaser.getProcessedText();
        String statistics = ceaser.frequencyTextAnalysis(cipherText);
        String allInfoAbout = ceaser.toString();
        //fast way to print out decrypted/encrypted text
        ceaser.printProccesedData();

        //Open file
        FileOperator fo = new FileOperator();
        fo.open("C:/plaintext");;
        ceaser.encrypt(fo.getText());
        cipherText = ceaser.getProcessedText();

        //Save to file
        fo.save(cipherText);
        //OR
        fo.save(Paths.get("C:/MY_SECRET_FOLDER/"), cipherText);

        String encryptedText = ceaser.getProcessedText();
        ceaser.printProccesedData();
        ceaser.frequencyTextAnalysis(encryptedText);
        fo.save(encryptedText);

        ceaser.decrypt(encryptedText);
        ceaser.printProccesedData();

        System.out.println(ceaser.toString());
    }
}
