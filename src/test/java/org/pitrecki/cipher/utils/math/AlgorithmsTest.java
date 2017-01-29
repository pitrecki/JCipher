package org.pitrecki.cipher.utils.math;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

/**
 * @author Piotr 'pitrecki' Nowak
 * Created by Pitrecki on 2016-12-24.
 */

class AlgorithmsTest
{
    @Test
    @DisplayName("Negative values should return same gcd value")
    void testNegativeArgumentsInGCDMethod() {
        double expected = 12;

        assertSoftly(softly -> {
            assertThat(Algorithms.gcd(-48, 180)).isEqualTo(expected);
            assertThat(Algorithms.gcd(48, -180)).isEqualTo(expected);
            assertThat(Algorithms.gcd(-48, -180)).isEqualTo(expected);
        });

    }

    @Test
    @DisplayName("Arrays of negative values shold return same gcd value")
    void testMoreThanTwoNegativeArgumentInGCDMethod() {
        double expected = 3;


        assertSoftly(softly -> {
            assertThat(Algorithms.gcd(-48,-180,-15)).isEqualTo(expected);
            assertThat(Algorithms.gcd(-48,-180, 15)).isEqualTo(expected);
            assertThat(Algorithms.gcd(-48, 180, 15)).isEqualTo(expected);

            assertThat(Algorithms.gcd(48, 180, -15)).isEqualTo(expected);

        });
    }

    @Test
    @DisplayName("Testing of gcd value computing")
    void testGcdResult() {
        double expected = 6;
        double actual = Algorithms.gcd(54, 24);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Testing of computing gcd value (more than 2 numbers")
    void testGcdResultForMoreThanTwoNumbers() {
        double expected = 1;
        double actual = Algorithms.gcd(1514564654,1212465645,563464533,12312);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Negative arguments should give same result of lcm")
    void testNegtiveArgumentsForLCM() {
        double expected = 720;

        assertSoftly(softly -> {
            assertThat(Algorithms.lcm(-48, -180)).isEqualTo(expected);
            assertThat(Algorithms.lcm(-48, 180)).isEqualTo(expected);
            assertThat(Algorithms.lcm(48, -180)).isEqualTo(expected);
        });
    }

    @Test
    @DisplayName("Testing of correct lcm value computing for more than 2 arguments")
    void testResultLCMForMoreThanTwoArguments() {
        double expected = 1.592437747744106E30;
        double acutal = Algorithms.lcm(1514564654,1212465645,563464533,12312);

        assertThat(acutal).isEqualTo(expected);
    }

    @Test
    @DisplayName("Testing of computing modularDivide inversion of number")
    void testModularInverseResultComuputing() {
        long expected = 21;
        long acutal = Algorithms.modInverse(5, 26);

        assertThat(acutal).isEqualTo(expected);
    }

    @Test
    @DisplayName("Same arguments should throws expection, when moudlar inverse is computing")
    void testSameArgumentsShouldThorwsExceptionWhenModularInverseIsComputing() {
        assertThatThrownBy(() ->Algorithms.modInverse(2, 2))
                .isInstanceOf(ArithmeticException.class);
    }
}