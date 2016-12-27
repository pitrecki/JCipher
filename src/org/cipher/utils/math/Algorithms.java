package org.cipher.utils.math;

import java.math.BigInteger;

/**
 * This class contains simple and useful algorithms needed in
 * the processing codes.
 *
 * @author Piotr 'pitrecki' Nowak
 * @version 0.5.2
 * Created by Pitrecki on 2016-11-25.
 */
public final class Algorithms
{
    /**
     * Protection against instance of this class!
     */
    private Algorithms() {
    }

    /**
     * <b>Greatest commonon diviser AKA gcd </b>
     * This aloghoritm use modifided version of Euclides alghorithm
     *
     * In arithmetic and computer programming, the extended Euclidean algorithm is an extension
     * to the Euclidean algorithm, which computes, besides the greatest common divisor of
     * integers a and b, the coefficients of Bézout's identity, that is integers x and y such that
     * a x + b y = gcd ( a , b ) . {\displaystyle ax+by=\gcd(a,b).} ax+by=\gcd(a,b).
     * This is a certifying algorithm, because the gcd is the only number that can simultaneously
     * satisfy this equation and divide the inputs. It allows one to compute also, with almost no
     * extra cost, the quotients of a and b by their greatest common divisor.
     *
     * For more inforamation look at:  <a href="https://en.wikipedia.org/wiki/Extended_Euclidean_algorithm"> LINK </a>
     *
     * @param p first number
     * @param q second number
     * @return computed gcd value
     */
    public static double gcd(double p, double q) {
        double remainder = p % q;
        if (remainder == 0)
            return q;
        return gcd(q, remainder);
    }

    /**
     * Calculate gcd for more than 2 numbers
     * @param values array or list numbers to compute
     * @return calculated gcd value
     */

    public static double gcd(double ... values) {
        double gdcResult = values[0];
        for (int i = 1; i < values.length; i++)
            gdcResult = gcd(gdcResult, values[i]);

        return  gdcResult;
    }

    /**
     * <b>Least common multiple AKA lcm</b>
     * This algorithm based on modified gcd algorithm.
     * In arithmetic and number theory, the least common multiple, lowest common multiple,
     * or smallest common multiple of two integers a and b, usually denoted by LCM(a, b), is
     * the smallest positive integer that is divisible by both a and b.[1] Since division of
     * integers by zero is undefined, this definition has meaning only if a and b are both
     * different from zero.[2] However, some authors define lcm(a,0) as 0 for all a, which is
     * the result of taking the lcm to be the least upper bound in the lattice of divisibility.
     * The LCM is familiar from elementary-school arithmetic as the "lowest common denominator"
     * (LCD) that must be determined before fractions can be added, subtracted or compared. The
     * LCM of more than two integers is also well-defined: it is the smallest positive integer that
     * is divisible by each of them.
     *
     * For more info look at <a href="https://en.wikipedia.org/wiki/Least_common_multiple"> LINK </a>
     *
     * @param p first number
     * @param q second value
     * @return computed lcm value
     */
    public static double lcm(double  p, double q) {
        return (p * q / gcd(p, q));
    }


    /**
     * Calculate lcm for more than 2 numbers
     * @param values array or list of numbers to compute
     * @return calculated lcm value
     */

    public static double lcm(double ... values) {
        double lcmResult = values[0];
        for (int i = 1; i < values.length; i++)
            lcmResult = lcm(lcmResult, values[i]);

        return lcmResult;
    }

    /**
     * Calculate modular multiplicative inverse of number
     * @param a value to inverse
     * @param m the modulus
     * @return a^-1 mod m
     */

    public static long modInverse(long a, long m) {
        BigInteger aBigInteger = new BigInteger(String.valueOf(a));
        BigInteger mBigInteger = new BigInteger(String.valueOf(m));

        return aBigInteger.modInverse(mBigInteger).longValue();
    }
}
