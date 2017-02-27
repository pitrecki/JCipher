package org.pitrecki.cipher.utils.tools;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.pitrecki.cipher.utils.math.Matrix;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Piotr 'pitrecki' Nowak
 *         Created by Pitrecki on 2017-02-20.
 */
class MatrixUtilsTest
{
    private MatrixUtils<Integer> matrixUtils;
    private Integer[][] data;

    @BeforeEach
    void setUp() {
        data = new Integer[][]{
                {1, 2, 3},
                {0, 2, 1},
                {11, 23, 3}
        };
    }

    @Test
    @DisplayName("Test get row on matrix with specified column should array of integers")
    void testGetRowAsArrayShouldReturnArraysOfIntegers() {
        matrixUtils = new MatrixUtils<>(new Matrix<>(data));

        Integer[] actual = matrixUtils.getRowAsArray(2);
        Integer[] expected = new Integer[]{11,23,3};

        SoftAssertions.assertSoftly(softly -> {
            assertThat(actual).isEqualTo(expected);
            assertThat(actual).hasSameSizeAs(expected);
            assertThat(actual).containsExactlyInAnyOrder(expected);
            assertThat(actual).doesNotContainNull();
        });
    }

    @Test
    @DisplayName("Test get row as array should return array of integers")
    void testGetRowAsArrayShouldReturnArrayOfIntegers() {
        matrixUtils = new MatrixUtils<>(new Matrix<>(data));

        Integer[] actual = matrixUtils.getColumnAsArray(2);
        Integer[] expected = new Integer[]{3, 1, 3};

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Test get column matrix should return column based matrix")
    void testGetColumnMatrixShouldReturnNewVector() {
        matrixUtils = new MatrixUtils<>(new Matrix<>(data));

        Matrix<Integer> actual = matrixUtils.getColumnMatrix(1);
        Matrix<Integer> expected = new Matrix<>(new Integer[][]{{2}, {2}, {23}});

        assertThat(actual).isEqualToComparingFieldByField(expected);
    }

    @Test
    @DisplayName("Test get row matrix should return 1xcolumn size matrix with data from desired row")
    void testGetRowMatrixShouldRetrunDesiredRowAsMatrixWithData() {
        matrixUtils = new MatrixUtils<>(new Matrix<>(data));

        Matrix<Integer> actual = matrixUtils.getRowMatrix(0);
        Matrix<Integer> expected = new Matrix<>(new Integer[][]{{1,2,3}});

        assertThat(actual).isEqualToComparingFieldByField(expected);
    }
}