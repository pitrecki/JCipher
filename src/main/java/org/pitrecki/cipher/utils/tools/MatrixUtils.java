package org.pitrecki.cipher.utils.tools;

import org.pitrecki.cipher.utils.math.Matrix;

import java.lang.reflect.Array;

/**
 * This class deliver other functionality needed in matrix processing such
 * as creating vectors.
 *
 *
 * @author Piotr 'pitrecki' Nowak
 *         Created by Pitrecki on 2017-02-20.
 */
public class MatrixUtils<T>
{
    private Matrix<T> matrix;
    private T[][] tArray;

    public MatrixUtils(Matrix<T> matrix) {
        this.matrix = matrix;
    }

    public MatrixUtils(T[][] tArray) {
        this.tArray = tArray;
    }

    /**
     * Create single column T array based on entered column number by user
     *
     * @param columnNumber  column number of terms which will be created vector
     * @return new single column T array
     */

    @SuppressWarnings("unchecked T cast")
    public T[][] getColumn(int columnNumber) {
        T[][] tmpArray = (T[][]) Array.newInstance(tArray.getClass().getComponentType().getComponentType(), tArray.length, 1);
        for (int i = 0; i < tArray.length; i++)
            tmpArray[i][0] = tArray[i][columnNumber];

        return tmpArray;
    }

    /**
     * Create one diemension array based on column number
     *
     * @param columnNumber column number of terms which will be created vector
     * @return new T one dimension array
     */

    @SuppressWarnings("unchecked T cast")
    public T[] getColumnAsArray(int columnNumber) {
        T[] tmpArray = (T[]) Array.newInstance(tArray.getClass().getComponentType().getComponentType(), tArray.length);
        for (int i = 0; i < tArray.length; i++) {
            tmpArray[i] = tArray[i][columnNumber];
        }
        return tmpArray;
    }

    /**
     * Create row based matrix
     *
     * @param columnNumber column number of terms which will be created vector
     * @return new single column matrix
     */

    @SuppressWarnings("unchecked T cast")
    public Matrix<T> getColumnMatrix(int columnNumber) {
        Matrix<T> tmpMatrix = new Matrix<>(matrix.getRow(), 1);
        for (int i = 0; i < matrix.getRow(); i++) {
            tmpMatrix.setValueInMatrix(i, 0, matrix.getValue(i, columnNumber));
        }

        return tmpMatrix;
    }

}
