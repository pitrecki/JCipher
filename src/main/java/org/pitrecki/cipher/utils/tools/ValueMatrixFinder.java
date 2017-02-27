package org.pitrecki.cipher.utils.tools;

import org.pitrecki.cipher.utils.math.Matrix;

/**
 * @author Piotr 'pitrecki' Nowak
 *         Created by Pitrecki on 2017-02-25.
 */
public class ValueMatrixFinder<T>
{
    private int[] coordinates;

    public ValueMatrixFinder() {
        this.coordinates = new  int[2];
    }

    public int getRowCoordinate() {
        return coordinates[0];
    }

    public int getColumnCoordinate() {
        return coordinates[1];
    }

    public void findCoordinateOfValue(Matrix<T> matrix, T value) {
        for (int rowIndex = 0; rowIndex < matrix.getRow(); rowIndex++) {
            for (int columnIndex = 0; columnIndex < matrix.getColumn(); columnIndex++) {
                if (matrix.getValue(rowIndex, columnIndex).equals(value)) {
                    coordinates[0] = rowIndex;
                    coordinates[1] = columnIndex;
                    break;
                }
            }
        }
    }
}
