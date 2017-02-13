package org.pitrecki.cipher.utils.math;

import org.pitrecki.cipher.utils.typewrappers.AnyNumberArrayConverter;
import org.pitrecki.cipher.utils.typewrappers.DoubleArrayConverter;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.stream.Stream;

/**
 * In mathematics, a matrix (plural matrices) is a rectangular array[1] of numbers, symbols, or expressions, arranged
 * in rows and columns. For example, the dimensions of the
 * matrix below are 2 × 3 (read "two by three"), because there are two rows and three columns.
 * Example
 *  2  3  4
 *  4 -1  4
 *
 * <b>Contribution JAMA : A Java Matrix Package</b>
 * <a href="http://math.nist.gov/javanumerics/jama/#Authors"> JAMA </a>
 * {@link #inverse()}
 *
 * @author Piotr 'pitrecki' Nowak
 *
 * Created by Pitrecki on 2016-11-10.
 */
public class Matrix<T>
{
    private T[][] data;
    private int column;
    private int row;

    /**
     * This constructor generate matrix from imputed data
     * @param data any type of Objects
     */
    public Matrix(T[][] data) {
        this.data = data;
        this.row = data.length;
        this.column = data[0].length;
    }

    /**
     * Constructor create matrix with dimension MxN
     * @param column -> N
     * @param row -> M
     */
    @SuppressWarnings("unchecked T cast")
    public Matrix(int row, int column) {
        this.column = column;
        this.row = row;
        this.data = (T[][]) new Object[row][column];
        emptyMatrixInit();
    }

    @SuppressWarnings("unchecked T cast")
    public Matrix(Matrix matrix) {
        this.column = matrix.getColumn();
        this.row = matrix.getRow();
        this.data = (T[][]) matrix.getData();
    }

    /**
     *  Constructor generate square matrix MxM
     * @param square
     */
    public Matrix(int square) {
        this(square, square);
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    public T[][] getData() {
        return data;
    }

    public T getValue(int row, int index) {
        return data[row][index];
    }

    public String getDimension() {
        return getColumn() + "x" + getRow();
    }

    public void setData(T[][] data) {
        this.data = data;
    }

    /**
     * This method fill matrix with default values equals 0.0
     */
    private void emptyMatrixInit() {
        Arrays.stream(getData())
                .forEach(objects -> Arrays.fill(objects, null));
    }

    /**
     * Adding value to the matrix
     * @param row -> M
     * @param column  -> N
     * @param value -> Any value
     */
    public void setValueInMatrix(int row, int column, T value) {
        this.data[row][column] = value;
    }

    public boolean dataAreNumbers() {
        return data.getClass().isAssignableFrom(Number.class);
    }

    //region Basic mathematics operation
    /**
     * ================ ATTENTION USE ONLY WHEN MATRIX CONTAINS ANY TYPE OF NUMBERS =========
     * In mathematics, matrix multiplication or the matrix product is a binary operation that
     * produces a matrix from two matrices. The definition is motivated by linear equations
     * and linear transformations on vectors, which have numerous applications in applied
     * mathematics, physics, and engineering.[1][2] In more detail, if A is an n × m matrix
     * and B is an m × p matrix, their matrix product AB is an n × p matrix, in which the m entries
     * across a row of A are multiplied with the m entries down a columns of B and summed to produce
     * an entry of AB. When two linear transformations are represented by matrices, then the matrix
     * product represents the composition of the two transformations.
     *
     * @param B Matrix
     * @return result of the multiplication of two matrices
     * @throws MatrixException only when matrix A M dim not equals matrix B dim N.
     */

    @SuppressWarnings("works only for numbers!")
    public Matrix multiply(Matrix B) throws MatrixException {
        Double[][] tmpArray = new Double[B.getRow()][B.getColumn()];
        if (checkDimension(B)) {
            double value = 0;
            for (int rowIndexMatrixA = 0; rowIndexMatrixA < getRow(); rowIndexMatrixA++) {
                for (int columnIndexMatrixB = 0; columnIndexMatrixB < B.getColumn(); columnIndexMatrixB++) {
                    for (int columnIndexMatrixA = 0; columnIndexMatrixA < getColumn(); columnIndexMatrixA++) {
                        value += (Double.parseDouble(getValue(rowIndexMatrixA, columnIndexMatrixA).toString()) * (Double.parseDouble(B.getData()[columnIndexMatrixA][columnIndexMatrixB].toString())));
                    }
                    tmpArray[rowIndexMatrixA][columnIndexMatrixB] = value;
                    value = 0;
                }
            }
            B.setData(tmpArray);
        }
        else {
            throw new MatrixException("Incorrect dimenesion, expected value: " + getDimension());
        }

        return B;
    }

    /**
     *  Multiplication every element in matrix by scalar number
     *
     * @param scalar is a number
     * @return same Matrix multiplied by scalar
     */

    @SuppressWarnings("unchecked T cast")
    public Matrix scalarMultiply(double scalar) {
        for (int i = 0; i < getRow(); i++)
            for (int j = 0; j < getColumn(); j++) {
                Double multipliedValue = scalar * (Double.parseDouble(getValue(i, j).toString()));
                setValueInMatrix(i, j, (T) multipliedValue);
            }

        return this;
    }
    //endregion

    /**
     * reflect A over its main diagonal (which runs from top-left to bottom-right) to obtain AT
     * write the rows of A as the columns of AT
     * write the columns of A as the rows of AT
     *
     * FOR BETTER EXPLANATION SEE <a href="https://en.wikipedia.org/wiki/Transpose"> LINK </a>
     *
     * @return new transposed matrix
     */

    @SuppressWarnings("unchecked T cast")
    public Matrix transpose() {
        T[][] tmpMatrix = (T[][]) Array.newInstance(data.getClass().getComponentType().getComponentType(),
                getColumn(), getRow());
        for (int row = 0; row < getRow(); row++) {
            for (int column = 0; column < getColumn(); column++) {
                tmpMatrix[column][row] = getValue(row, column);
            }
        }
        injectData(tmpMatrix);
        return this;
    }

    /**
     *  Determinant is value calculated from the elements of the square matrix.
     *  det(A); detA or |A|
     *  Good examples for calculating the determinant, visit:
     *  1. <a href="http://www.mathsisfun.com/algebra/matrix-determinant.html"> mathisfun </a>
     *  2. <a href="https://www.math.ucdavis.edu/~daddel/linear_algebra_appl/Applications/Determinant/Determinant/node3.html"> UC </a>
     * @param matrix matrix used for calculations
     * @return calculated determinant
     */

    public Double determinant(Matrix matrix) throws MatrixException {
        switch (matrix.getRow()) {
            case 0:
               throw new MatrixException("Size can not be 0");
            case 1:
                return Double.parseDouble(matrix.getValue(0,0).toString());
            case 2:
                return (Double.parseDouble(matrix.getValue(0,0).toString()) * Double.parseDouble(matrix.getValue(1,1).toString())) - (Double.parseDouble(getValue(0,1).toString())
                        * Double.parseDouble(matrix.getValue(1,0).toString()));
            default:
                double[][] primitiveData = Arrays.stream((Double[][]) getData())
                        .map(doubles -> Arrays.stream(doubles)
                                .mapToDouble(Double::doubleValue)
                                .toArray())
                        .toArray(double[][]::new);
                return new Jama.Matrix(primitiveData).det();
        }
    }

    /**
     * A submatrix of a matrix is obtained by deleting any collection of rows and/or columns.
     * @param matrix matrix used for calculations
     * @param row number of rows new submatrix
     * @param column number of columns new submatrix
     * @return submatrix of matrix given as method argument
     */

    @SuppressWarnings("It may not work properly")
    private Matrix subMatrix(Matrix matrix, int row, int column) {
        Double[][] tmpData = new Double[matrix.getRow() -1][matrix.getColumn() - 1];
        int rowNumber = -1;

        for (int i = 0; i < matrix.getRow(); i++) {
            if (i == row)
                continue;
            rowNumber++;
            int columnNumber = -1;
            for (int j = 0; j < matrix.getColumn(); j++) {
                if (j == column)
                    continue;
                tmpData[rowNumber][++columnNumber] = Double.parseDouble(matrix.getValue(i, j).toString());
            }

        }
        return new Matrix(tmpData);
    }

    /**
     * Changes sign of specified index in matrix
     * @param index current index to change sign
     * @return sign changed sign
     */

    private int changeSign(int index) {
        return index%2 == 0 ? 1 : -1;
    }

    /**
     * The cofactors feature prominently in Laplace's formula for the expansion of determinants, which is
     * a method of computing larger determinants in terms of smaller ones. Given the n × n  (aij),
     * the determinant of A (denoted det(A)) can be written as the sum of the cofactors of any row or column of
     * the matrix multiplied by the entries that generated them.
     * @param matrix matrix used for calculations
     *
     * @return new cofactored matrix
     */

    @SuppressWarnings("Not work properly")
    public Matrix cofactor(Matrix matrix) throws MatrixException {
        Double[][] tmpData = new Double[matrix.getRow()][matrix.getColumn()];
        for (int i = 0; i < matrix.getRow(); i++)
            for (int j = 0; j < matrix.getColumn(); j++)
                tmpData[i][j] = changeSign(j) * changeSign(i) * determinant(subMatrix(matrix, i, j));

        return new Matrix(tmpData);
    }

    /**
     * The inverse of a square matrix A, sometimes called a reciprocal matrix, is a matrix A^(-1) such that
     *
     * AA^(-1)=I,
     *
     * where
     * - I is the identity matrix.
     *
     * A square matrix A has an inverse if the determinant |A|!=0. The so-called invertible matrix theorem
     * is major result in linear algebra which associates the existence of a matrix inverse with a number
     * of other equivalent properties. A matrix possessing an inverse is called nonsingular, or invertible.
     *
     * The matrix inverse of a square matrix m may be taken in the Wolfram Language using the function Inverse[m].
     *
     * For a 2×2 matrix
     * A= [a b;
     * c d],
     *
     * the matrix inverse is
     *
     * A^(-1) = (1/det(A))[d -b; = (1/(ad-bc)) [d -b;
     *                    -c a]		            -c a]
     *
     * @return A^-1
     */

    public Matrix inverse() {
        Double[][] tmpObjMatrixValues;

        /*
            Check if data in Matrix are numbers and NOT double. If true convert this do Double type by parsing
            to String, otherwise skip and jump to 311 line
        */
        if (!isMatrixDataIsDoubleType()) {
            AnyNumberArrayConverter<Number, Double> anyNumberArrayConverter = new AnyNumberArrayConverter<>(Double.class);
            tmpObjMatrixValues = anyNumberArrayConverter.convertArray((Number[][]) getData());
            setData(((T[][]) tmpObjMatrixValues));
        }

        DoubleArrayConverter doubleArrayConverter = new DoubleArrayConverter();

        double[][] tmpPrMatrixVals = doubleArrayConverter.convertToPrimitive((Double[][]) data);

        tmpPrMatrixVals = new Jama.Matrix(tmpPrMatrixVals).inverse().getArray();

        tmpObjMatrixValues = doubleArrayConverter.convertToObject(tmpPrMatrixVals);

        setData((T[][]) tmpObjMatrixValues);
        return this;
    }

    /**
     * Check if matrix data are doubles
     *
     * @return true if it is
     *         false if not
     */

    private boolean isMatrixDataIsDoubleType() {
        return getData().getClass().isAssignableFrom(Double[][].class);
    }

    /**
     * Definition
     * det(A) * inv(A)
     * {@link #determinant(Matrix)}
     * {@link #inverse()}
     * @return adjugated Matrix
     */

    public Matrix adjugate() throws MatrixException {
        double detVal = determinant(this);
        inverse();
        return scalarMultiply(detVal);
    }


    /**
     * Clear all data from
     * {@link #data}
     * {@link #row}
     * {@link #column}
     */

    public void flush() {
        setData(null);
        column = row = 0;
    }

    /**
     * This method using functionality from {@link #flush()}
     * Clear all data and replace objects fields with new values
     * @param matrix changed matrix to inject
     */

    @SuppressWarnings("unchecked T cast")
    public void injectFlushedData(Matrix matrix) {
        flush();
        this.column = matrix.getColumn();
        this.row = matrix.getRow();
        this.data = (T[][]) matrix.getData();
    }

    /**
     * This method using functionality from {@link #flush()}
     * {@link #transpose()}
     * Clear all data and replace objects fields with new values
     * @param data T type 2D array
     */

    public void injectData(T[][] data) {
        flush();
        column = data[0].length;
        row = data.length;
        this.data = data;
    }

    /**
     * Replace every element with mod modValue divide by modulo
     * @param modValue
     * @return this matrix with divide values by modularDivide
     */

    @SuppressWarnings("unchecked T cast")
    public Matrix modularDivide(double modValue) {
        for (int i = 0; i < getRow(); i++) {
            for (int j = 0; j < getColumn(); j++) {
                Double modResult = ((Double) getValue(i, j)) % modValue;
                if (modResult < 0)
                    modResult += modValue;
                setValueInMatrix(i, j, (T) modResult);
            }
        }

        return this;
    }

    /**
     * Protection before make any computing especially {@link #multiply(Matrix)}
     * @param B is matrix
     * @return true if  matrix A row lengths equals matrix B columns
     */
    private boolean checkDimension(Matrix B) {
        return this.getColumn() == B.getRow();
    }

    /**
     * Protection against jagged arrays of entered data
     * @param data 2D-array to check
     * @return true only when every row lengths of 2D-Array is same
     */

    private boolean isDataAreJagged (T[][] data) {
        int expectedAmountOfElements = data.length * data[0].length;

        int actualAmountOfElements = 0;
        for (T[] objArray : data)
            actualAmountOfElements += objArray.length;

        return expectedAmountOfElements == actualAmountOfElements;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        Stream.of(getData())
                .map(Arrays::toString)
                .forEach(line -> builder.append(line + System.lineSeparator()));
        return builder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Matrix matrix = (Matrix) o;
        return column == matrix.column &&
                row == matrix.row &&
                Arrays.equals(data, matrix.data);
    }
}
