package org.pitrecki.cipher.utils.typewrappers;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Piotr 'pitrecki' Nowak
 *         Created by Pitrecki on 2017-02-09.
 */
class DoubleArrayConverterTest
{
    private DoubleArrayConverter doubleArrayConverter;

    @BeforeEach
    void setUp() {
        this.doubleArrayConverter = new DoubleArrayConverter();
    }

    @Test
    @DisplayName("Test Double 2D conversion to double 2d array")
    void testShouldReturnPrimitiveDoubleArray() {
        Double[][] doubles = {{2d, 1d}, {4d, 1d}};

        double[][] actual = doubleArrayConverter.convertToPrimitive(doubles);
        double[][] expected = {{2d, 1d}, {4d, 1d}};
        assertThat(actual).containsExactly(expected);
    }

    @Test
    @DisplayName("Test double 2D conversion to Double 2d array")
    void testShouldReturnObjectDoubleArray() {
        double[][] doubles = {{2d, 1d}, {4d, 1d}};

        Double[][] actual = doubleArrayConverter.convertToObject(doubles);
        Double[][] expected = {{2d, 1d}, {4d, 1d}};
        assertThat(actual).containsExactly(expected);
    }

    @Test
    @DisplayName("Test remaining features")
    void testOtherFeatures() {
        double[][] expected = {{2d, 1d}, {4d, 1d}};

        Double[][] actual = doubleArrayConverter.convertToObject(expected);

        SoftAssertions.assertSoftly(softly -> {
            assertThat(actual).isNotNull();
            assertThat(actual).hasSameSizeAs(expected);
        });
    }
}