package org.pitrecki.cipher.utils.tools;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.pitrecki.cipher.utils.math.Matrix;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Piotr 'pitrecki' Nowak
 *         Created by Pitrecki on 2017-02-26.
 */
class ValueMatrixFinderTest
{
    private Matrix<Double> matrix;
    private ValueMatrixFinder<Double> valueMatrixFinder;

    @BeforeEach
    void setUp() {
        this.matrix = new Matrix<>(new Double[][]{
                {3123d, 1232312d, 22d},
                {1122d, 236556d, 2221112d},
                {-0.12312d, -23232d, -1d}
        });

        this.valueMatrixFinder = new ValueMatrixFinder<>();
    }

    @Test
    @DisplayName("Test get row coordinates should return row number")
    void testGetRowCoordinateShouldReturnRowNumberOfDesiredValue() {
        valueMatrixFinder.findCoordinateOfValue(matrix, 22d);

        int actual = valueMatrixFinder.getRowCoordinate();
        assertThat(actual).isEqualTo(0);
    }

    @Test
    @DisplayName("Test get column coordinates should return column number")
    void testGetColumnCoordinateShouldReturnColumnNumberOfDesiredValue() {
        valueMatrixFinder.findCoordinateOfValue(matrix, -23232d);

        int actual = valueMatrixFinder.getColumnCoordinate();
        assertThat(actual).isEqualTo(1);
    }
}