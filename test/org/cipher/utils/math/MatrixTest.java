package org.cipher.utils.math;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Piotr 'pitrecki' Nowak
 * Created by Pitrecki on 2016-12-27.
 */

//todo modular test
class MatrixTest
{
    private Matrix A;

    @BeforeEach
    void setUp() {
        Double[][] data = new Double[][]{{1d, 2d, 3d},
                                         {4d, 5d, 6d},
                                         {7d, 8d, 9d}};
        this.A = new Matrix(data);
    }

    @Test
    @DisplayName("Testing of correct computing determinant of matrix 3x3")
    void testCorrectDetValue() throws MatrixException {
        double expect = 0;
        double actual = A.determinant(A);

        assertEquals(expect, actual);
    }

    @Test
    @DisplayName("Testing of correct matrix transposition")
    void testMatrixTranspose() {
        Matrix expectedTransposition = new Matrix(new Double[][]{
                {1.0, 4.0, 7.0},
                {2.0, 5.0, 8.0},
                {3.0, 6.0, 9.0}
        });

        assertEquals(expectedTransposition.toString(), A.transpose().toString());
    }

    @Test
    @DisplayName("Testing of multiply 2 matrices")
    void testMatrixMultiply() throws MatrixException {
        Matrix expectedResult = new Matrix(new Double[][]{
                {30d, 36d, 42d},
                {66d, 81d, 96d},
                {102d, 126d, 150d}
        });

        assertEquals(expectedResult.toString(), A.multiply(A).toString());
    }

    @Test
    @DisplayName("Multiply matrix by 2.5")
    void testMultiplyByScalar() {
        Matrix expectedResult = new Matrix(new Double[][]{
                {2.5, 5d, 7.5d},
                {10d, 12.5, 15d},
                {17.5, 20d, 22.5d}
        });

        assertEquals(expectedResult.toString(), A.scalarMultiply(2.5).toString());
    }

    @Test
    @DisplayName("Try to adjugate matrix, in this case should return 0 and matrix is singular Exception is exptected")
    void testMatrixAdjugate() throws MatrixException {
        assertThrows(RuntimeException.class, () -> A.adjugate());
    }

    @Test
    @DisplayName("Inverse of 2x2 Matrix")
    void testMatrixInversion() {
        String expectedResult = "[1.0, 0.0]\r\n" +
                                 "[0.0, 1.0]\r\n";

        A.setData(new Double[][]{
                {1d, 0d},
                {0d, 1d}
        });

        String actual = A.inverse().toString();

        assertEquals(expectedResult, actual);
    }

    @Test
    @DisplayName("Testing of cakcuation modular matrix, modulo number is 1.0")
    void testModularMatrix() {
        Matrix expectedMatrix = new Matrix(new Double[][] {
                {0d, 0d},
                {0d, 0d}
        });

        A.setData(new Double[][] {
                {1d, 2d},
                {2d, 1d}
        });

        assertEquals(expectedMatrix.toString(), A.modular(1).toString());
    }
}