package org.pitrecki.cipher.utils;

import org.pitrecki.cipher.utils.math.Matrix;

import java.lang.reflect.Array;

/**
 * Creating encrpytion matrix with selected parameters
 *
 * @author Piotr 'pitrecki' Nowak
 *
 * Created by Pitrecki on 2016-12-22.
 */
public final class EncryptMatrixGenerator<T>
{
    private final Class<T> clazzType;

    private int column;
    private int row;
    private Matrix matrix;

    private EncryptMatrixGenerator(EncryptMatrixGeneratorBuilder<T> builder) {
        this.clazzType = builder.clazzType;
        this.column= builder.column;
        this.row = builder.row;
        createEncryptMatrix();
    }

    public Matrix getMatrix() {
        return matrix;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    public int getSize() {
        if (column == row)
            return column;
        return  -1;
    }

    @SuppressWarnings("unchecked T cast")
    private void createEncryptMatrix() {
        T[][] genericEncryptMatrix = ((T[][]) Array.newInstance(clazzType, row, column));
        matrix = new Matrix(genericEncryptMatrix);
    }

    @SuppressWarnings("unchecked T cast")
    public void fill(T ... values) {
        T[][] tmpGenericData = (T[][]) matrix.getData();
        int index = 0;
        for (int i = 0; i < tmpGenericData.length; i++) {
            for (int j = 0; j < tmpGenericData[i].length; j++) {
                tmpGenericData[i][j] = values[index++];
            }
        }

        matrix.setData(tmpGenericData);
    }



    public static class EncryptMatrixGeneratorBuilder<T>
    {
        //default values
        private int column = 5;
        private int row = 5;

        private final Class<T> clazzType;


        public EncryptMatrixGeneratorBuilder(Class<T> clazzType) {
            if (clazzType == null)
                throw new IllegalArgumentException("Type can not be null");

            this.clazzType = clazzType;

        }

        public EncryptMatrixGeneratorBuilder withSize(int size) {
            if (size <= 0)
                throw new IllegalArgumentException("Size can not be negative");
            this.column = this.row = size;

            return  this;
        }

        public EncryptMatrixGeneratorBuilder withColumn(int column) {
            if (column < 0)
                throw new IllegalArgumentException("Column size can not be negative");
            this.column = column;

            return  this;
        }


        public EncryptMatrixGeneratorBuilder withRow(int row) {
            if (row < 0)
                throw new IllegalArgumentException("Row size can to be negative");
            this.row = row;

            return this;
        }

        public EncryptMatrixGenerator build() {
            return new EncryptMatrixGenerator(this);
        }
    }
}
