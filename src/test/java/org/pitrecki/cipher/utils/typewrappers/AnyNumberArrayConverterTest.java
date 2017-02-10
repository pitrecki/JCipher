package org.pitrecki.cipher.utils.typewrappers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

/**
 * @author Piotr 'pitrecki' Nowak
 *         Created by Pitrecki on 2017-02-09.
 */
class AnyNumberArrayConverterTest
{
    @Test
    @DisplayName("Test convert Integer 2D array to Float 2D array")
    void testConvertIntegerArrayToFloatArray() {
        AnyNumberArrayConverter<Integer, Float> anyNumberArrayConverter = new AnyNumberArrayConverter<>(Float.class);
        Integer[][] numbers = {{1, 2, 3}, {4, 5, 6}};

        Float[][] actual = anyNumberArrayConverter.convertArray(numbers);
        Float[][] expected = {{1f, 2f, 3f}, {4f, 5f, 6f}};
        assertSoftly(softly -> {
            assertThat(actual).hasSameSizeAs(expected);
            assertThat(actual).containsExactly(expected);
            assertThat(actual).isInstanceOf(Float[][].class);
            assertThat(actual[0][2]).isInstanceOf(Float.class).isNotNull().isEqualTo(3f);
        });
    }

    @Test
    @DisplayName("Test convert Double 2D array to Integer 2D array")
    void testConvertDoubleArrayToIntegerArray() {
        AnyNumberArrayConverter<Double, Integer> anyNumberArrayConverter = new AnyNumberArrayConverter<>(Integer.class);
        Double[][] numbers = {{1d, 2d, 3d}, {4d, 5d, 6d}};

        Integer[][] actual = anyNumberArrayConverter.convertArray(numbers);
        Integer[][] expected = {{1, 2, 3}, {4, 5, 6}};
        assertSoftly(softly -> {
            assertThat(actual).hasSameSizeAs(expected);
            assertThat(actual).containsExactly(expected);
            assertThat(actual).isInstanceOf(Integer[][].class);
            assertThat(actual[0][2]).isInstanceOf(Integer.class).isNotNull().isEqualTo(3);
            assertThat(actual).doesNotContainNull();
        });
    }
}