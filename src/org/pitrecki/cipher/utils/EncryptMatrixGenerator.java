package org.pitrecki.cipher.utils;

import org.pitrecki.cipher.utils.math.Matrix;

import java.lang.reflect.Array;

/**
 * Creating encrpytion matrix with selected parameters
 *
 * @author Piotr 'pitrecki' Nowak
 * @version 0.5.8
 * Created by Pitrecki on 2016-12-22.
 */
public final class EncryptMatrixGenerator<T>
{
    private final Class<T> clazzType;

    private int column;
    private int row;
    private Matrix matrix;

    private EncryptMatrixGenerator(CryptMatrixGeneratorBuilder<T> builder) {
        this.clazzType = builder.clazzType;
        this.column= builder.column;
        this.row = builder.row;
        createCryptMatrix();
    }

    @SuppressWarnings("T casting can cause problems")
    private void createCryptMatrix() {
        T[][] genericCryptMatrix = ((T[][]) Array.newInstance(clazzType, row, column));
        matrix = new Matrix(genericCryptMatrix);
    }

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

    public Matrix getGenereratedCryptMatrix() {
        return matrix;
    }

    public static class CryptMatrixGeneratorBuilder<T>
    {
        //default values
        private int column = 5;
        private int row = 5;

        private final Class<T> clazzType;


        public CryptMatrixGeneratorBuilder(Class<T> clazzType) {
            if (clazzType == null)
                throw new IllegalArgumentException("Type can not be null");

            this.clazzType = clazzType;

        }

        public CryptMatrixGeneratorBuilder withSize(int size) {
            if (size <= 0)
                throw new IllegalArgumentException("Size can not be negative");
            this.column = this.row = size;

            return  this;
        }

        public CryptMatrixGeneratorBuilder withColumn(int column) {
            if (column < 0)
                throw new IllegalArgumentException("Column size can not be negative");
            this.column = column;

            return  this;
        }


        public CryptMatrixGeneratorBuilder withRow(int row) {
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
