package otherStuff.benchmarksTests;

import org.pitrecki.benchmark.Benchmark;
import org.pitrecki.cipher.ciphtypes.Cipher;
import org.pitrecki.cipher.ciphtypes.substition.simple.CeasarCipher;

/**
 * @author Piotr 'pitrecki' Nowak
 *         Created by Pitrecki on 2017-01-05.
 */
class CeaserCipherBenchmarkTest
{
    public static void main(String[] args) {
        Cipher ceaser = new CeasarCipher();

        ceaser.encrypt("abajhasihiofhsiofhsdafiohasdiofdhsf");
        String encryptedText = ceaser.getProcessedText();

        for (int i = 0; i < 1e5; i++) {
            Benchmark.measureTime(() ->
                    ceaser.frequencyTextAnalysis(encryptedText), Benchmark.Options.YES);
        }

        Benchmark.calculateStatistics(Benchmark.Options.YES);
    }
}
