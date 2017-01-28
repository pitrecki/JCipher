package otherStuff.benchmarkTests;

import org.pitrecki.benchmark.Benchmark;
import org.pitrecki.cipher.utils.AsciiGenerator;

import java.util.stream.IntStream;

/**
 * @author Piotr 'pitrecki' Nowak
 *         Created by Pitrecki on 2017-01-02.
 */
public class BenchmarkTests
{
    public static void main(String[] args) {
        System.out.println("Perform test for lower caseses");
        for (int i = 0; i < 100; i++)
            Benchmark.measureTime(AsciiGenerator::lowerCaseAlphabetGenerator,Benchmark.Options.NO);


        System.out.println("Perform tests for lower case using other option");
        char[] chars = new char[26];
        for (int i = 0; i < 100; i++) {
            Benchmark.measureTime(() -> IntStream.range(0, chars.length)
                    .mapToObj(value -> chars[value] = (char) ('A' + value))
                    .toArray(), Benchmark.Options.NO);
        }

    }

    private static void createAlphabet() {
        char[] chars = new char[26];
        for (int i = 0; i < chars.length; i++) {
            chars[i] = (char) ('A' + i);
        }
    }
}
