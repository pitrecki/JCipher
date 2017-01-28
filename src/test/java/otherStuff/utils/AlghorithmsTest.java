package otherStuff.utils;

import org.pitrecki.cipher.utils.math.Algorithms;
import org.pitrecki.cipher.utils.math.MatrixException;

/**
 * Created by Pitrecki on 2016-11-25.
 */
public class AlghorithmsTest
{
    public static void main(String[] args) throws MatrixException {
        System.out.println(Algorithms.gcd(48,180));
        double result = Algorithms.gcd(48, 180);
        System.out.println(Algorithms.gcd(result, 15));
        System.out.println(Algorithms.gcd(48,180,15));
        System.out.println(Algorithms.lcm(48, 180));
        System.out.println(Algorithms.lcm(48,180,15));

        System.out.println(Algorithms.gcd(1514564654,1212465645,563464533,12312));
        System.out.println(Algorithms.lcm(1514564654,1212465645,563464533,12312));
        System.out.println(Algorithms.gcd(9%26, 26));

        System.out.println(Algorithms.modInverse(5, 26));
    }
}
