package org.pitrecki.cipher.utils.math;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * @author Piotr 'pitrecki' Nowak
 * Created by Pitrecki on 2016-12-27.
 */

//todo modularDivide test
class MatrixTest
{
    private Matrix<?> A;

    @BeforeEach
    void setUp() {
        Double[][] data = new Double[][]{{1d, 2d, 3d},
                                         {4d, 5d, 6d},
                                         {7d, 8d, 9d}};
        A = new Matrix<>(data);
    }

    @Test
    @DisplayName("Testing of correct computing determinant of matrix 3x3")
    void testCorrectDetValue() throws MatrixException {
        double expected = 0;
        double actual = A.determinant(A);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Testing of correct matrix transposition")
    void testMatrixTranspose() {
        Matrix<Double> expectedTransposition = new Matrix<>(new Double[][]{
                {1.0, 4.0, 7.0},
                {2.0, 5.0, 8.0},
                {3.0, 6.0, 9.0}
        });

        assertThat(A.transpose().getData()).isEqualTo(expectedTransposition.getData());

    }

    @Test
    @DisplayName("Testing of multiply 2 matrices")
    void testMatrixMultiply() throws MatrixException {
        Matrix<Double> expectedResult = new Matrix<>(new Double[][]{
                {30d, 36d, 42d},
                {66d, 81d, 96d},
                {102d, 126d, 150d}
        });

        assertThat(A.multiply(A).getData()).isEqualTo(expectedResult.getData());

    }

    @Test
    @DisplayName("Multiply matrix by 2.5")
    void testMultiplyByScalar() {
        Matrix<Double> expectedResult = new Matrix<>(new Double[][]{
                {2.5, 5d, 7.5d},
                {10d, 12.5, 15d},
                {17.5, 20d, 22.5d}
        });

        assertThat(A.scalarMultiply(2.5).getData()).isEqualTo(expectedResult.getData());
//        assertArrayEquals(expectedResult.getData(), A.scalarMultiply(2.5).getData());
    }

    @Test
    @DisplayName("Try adjugate matrix, in this case should return 0 and matrix is singular Exception is expected")
    void testMatrixAdjugate() throws MatrixException {
        assertThatThrownBy(() ->A.adjugate())
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("Inverse of 2x2 Matrix")
    void testMatrixInversion() {
        Matrix<Double> expectedMatrix = new Matrix<>(new Double[][] {
                {1d, 0d},
                {0d, 1d}
        });

        A = new Matrix<>(new Double[][]{
                {1d, 0d},
                {0d, 1d}
        });

        assertThat(A.inverse().getData()).isEqualTo(expectedMatrix.getData());
    }

    @Test
    @DisplayName("Testing of calculation modularDivide matrix, modulo number is 1.0")
    void testModularMatrix() {
        Matrix<Double> expectedMatrix = new Matrix<>(new Double[][] {
                {0d, 0d},
                {0d, 0d}
        });

        A= new Matrix<>(new Double[][] {
                {1d, 2d},
                {2d, 1d}
        });

        assertThat(A.modularDivide(1).getData()).isEqualTo(expectedMatrix.getData());
//        assertArrayEquals(expectedMatrix.getData(), A.modularDivide(1).getData());
    }

    @Test
    @DisplayName("Matrix is created by entered size, every elements should be set to null")
    void testForGenerateEmptyMatrix() {
        A = null;
        A = new Matrix(3);


        SoftAssertions.assertSoftly(softly ->{
            Stream.of(A.getData())
                    .forEach(objects -> Arrays.stream(objects)
                        .map(o -> assertThat(o).isNotNull()));

        });

//        assertAll("Check for every element is null", () -> {
//            Stream.of(A.getData()).forEach(objects -> Arrays.stream(objects).forEach(Assertions::assertNull));
//        });
    }
}