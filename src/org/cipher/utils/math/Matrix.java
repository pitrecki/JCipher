package org.cipher.utils.math;

import org.cipher.utils.Variant;

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
 * @version 0.0.5
 * Created by Pitrecki on 2016-11-10.
 */
public class Matrix
{
    private Object[][] data;
    private int column;
    private int row;

    /**
     * This construcotr generate matrix from inputed data
     * @param data any type of Objects
     */
    public Matrix(Object[][] data) {
        this.data = data;
        this.row = data.length;
        this.column = data[0].length;
    }

    /**
     * Constructor create matrix with dimension MxN
     * @param column -> N
     * @param row -> M
     */
    public Matrix(int row, int column) {
        this.column = column;
        this.row = row;
        this.data = new Object[row][column];
        emptyMatrixInit();
    }

    public Matrix(Matrix matrix) {
        this.column = matrix.getColumn();
        this.row = matrix.getRow();
        this.data = matrix.getData();
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

    public Object[][] getData() {
        return data;
    }


    public String getDimension() {
        return this.getColumn() + "x" + this.getRow();
    }

    public void setData(Object[][] data) {
        this.data = data;
    }

    /**
     * This method fill matrix with default values eqauls 0.0
     */
    private void emptyMatrixInit() {
//        for (int i = 0; i < getRow(); i++) {
        for (Object[] objects : this.getData())
            Arrays.fill(objects, null);
    }

    /**
     * Adding value to the matrix
     * @param row -> M
     * @param column  -> N
     * @param value -> Any value
     */
    public void setValueInMatrix(int row, int column, Object value) {
        this.data[row][column] = value;
    }


    //region Basic mathematic operation
    /**
     * ================ ATENTION USE ONLY WHEN MATRIX CONTATINS ANY TYPE OF NUMBERS =========
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
     * @throws MatrixException only when matrix A M dim not equls matrix B dim N.
     */

    @SuppressWarnings("works only for numbers!")
    public Matrix multiply(Matrix B) throws MatrixException {
        Double[][] tmpArray = new Double[B.getRow()][B.getColumn()];
        if (checkDimension(B)) {
            double value = 0;
            for (int rowIndexMatrixA = 0; rowIndexMatrixA < this.getRow(); rowIndexMatrixA++) {
                for (int columnIndexMatrixB = 0; columnIndexMatrixB < B.getColumn(); columnIndexMatrixB++) {
                    for (int columnIndexMatrixA = 0; columnIndexMatrixA < this.getColumn(); columnIndexMatrixA++) {
                        value += (Double.parseDouble(this.getData()[rowIndexMatrixA][columnIndexMatrixA].toString()) * (Double.parseDouble(B.getData()[columnIndexMatrixA][columnIndexMatrixB].toString())));
                    }
                    tmpArray[rowIndexMatrixA][columnIndexMatrixB] = value;
                    value = 0;
                }
            }
            B.setData(tmpArray);
        }
        else {
            throw new MatrixException("Incorect dimenesion, expected value: " + getDimension());
        }

        return B;
    }

    /**
     *  Multiplication every element in matrix by scalar number
     *
     * @param scalar is a number
     * @return same Matrix multiplied by scalar
     */

    public Matrix scalarMultiply(double scalar) {
        for (int i = 0; i < this.getRow(); i++)
            for (int j = 0; j < this.getColumn(); j++)
                setValueInMatrix(i, j, (scalar * Double.parseDouble(getData()[i][j].toString())));

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

    public Matrix transpose() {
        Object[][] tmpMatrix = new Object[getColumn()][getRow()];
        for (int row = 0; row < this.getRow(); row++) {
            for (int column = 0; column < this.getColumn(); column++) {
                tmpMatrix[column][row] = this.getData()[row][column];
            }
        }
        injectFlushedData(new Matrix(tmpMatrix));
        return this;
    }

    /**
     *  Determinat is value calculated fromthe elements of the square matrix.
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
                return Double.parseDouble(matrix.getData()[0][0].toString());
            case 2:
                return (Double.parseDouble(matrix.getData()[0][0].toString()) * Double.parseDouble(matrix.getData()[1][1].toString())) - (Double.parseDouble(this.getData()[0][1].toString())
                        * Double.parseDouble(matrix.getData()[1][0].toString()));
            default:
                double sum = 0.0;
                for (int i = 0; i < matrix.getColumn(); i++)
                    sum += changeSign(i) * (Double.parseDouble(matrix.getData()[0][i].toString())) * determinant(subMatrix(matrix, 0, i));
                return sum;
        }

//        return Double.valueOf(-1);
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
                tmpData[rowNumber][++columnNumber] = Double.parseDouble(matrix.getData()[i][j].toString());
            }

        }
        return new Matrix(tmpData);
    }

    /**
     * Changes sign of specified index in matrix
     * @param index current index to change sigm
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
        /*
            Old code here
            return (transpose().cofactor(this).scalarMultiply(1.0/determinant(this)));
         */
        
        double[][] tmpMatrixVals = new double[this.getRow()][this.getColumn()];
        for (int i = 0; i < getData().length; i++) {
            for (int j = 0; j < getData()[i].length; j++) {
                tmpMatrixVals[i][j] = Double.valueOf(getData()[i][j].toString());
            }
        }
        tmpMatrixVals = new Jama.Matrix(tmpMatrixVals).inverse().getArray();

        Double[][] tmpMatrixConvertedToObject = Arrays.stream(tmpMatrixVals).map(doubles ->
                Arrays.stream(doubles).boxed().toArray(Double[]::new)).toArray(Double[][]::new);

        this.setData(tmpMatrixConvertedToObject);
        return this;
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
        this.setData(null);
        this.column = this.row = 0;
    }

    /**
     * This method using functionality from {@link #flush()}
     * Clear all data and replace objects fields with new values
     * @param matrix changed matrix to inject
     */

    public void injectFlushedData(Matrix matrix) {
        flush();
        this.column = matrix.getColumn();
        this.row = matrix.getRow();
        this.data = matrix.getData();
    }

    /**
     * Convert Double type data values into Integer type data values
     * @see org.cipher.ciphtypes.polygraphic.HillCipher
     * {@link org.cipher.ciphtypes.polygraphic.HillCipher#cipherProccessing(String, Variant)}
     *
     * @return matrix with changed data type.
     */

    public Matrix convertDoubleDataToInteger() {
        Integer[][] intVals = new Integer[this.getRow()][this.getColumn()];
        for (int i = 0; i < this.getRow(); i++) {
            for (int j = 0; j < this.getColumn(); j++) {
                long  roundValue =  Math.round((Double) getData()[i][j]);
                intVals[i][j] = ((int) roundValue);
            }
        }
        setData(intVals);
        return this;
    }

    /**
     * Replace every element with mod value divide by modul
     * @param value
     * @return this matrix with divide values by modular
     */

    public Matrix modular(double value) {
        for (int i = 0; i < this.getRow(); i++) {
            for (int j = 0; j < this.getColumn(); j++) {
                Double modResult = ((Double) getData()[i][j]) % value;
                if (modResult < 0)
                    modResult += value;
                setValueInMatrix(i, j, modResult);
            }
        }

        return this;
    }

    /**
     * Protection before make any computing especially {@link #multiply(Matrix)}
     * @param B is matrix
     * @return true if  matrix A row lengths equals matrix B colmums
     */
    private boolean checkDimension(Matrix B) {
        return this.getColumn() == B.getRow();
    }

    /**
     * Protection agains jagged arrays in inputed data
     * @param data 2D-array to check
     * @return true only when every row lengths of 2D-Array is same
     */

    private boolean isDataAreJagged (Object[][] data) {
        int epectedAmountOfElements = data.length * data[0].length;

        int actutalAmountOfElements = 0;
        for (Object[] objArray : data)
            actutalAmountOfElements += objArray.length;

        return epectedAmountOfElements == actutalAmountOfElements;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        Stream.of(getData()).map(Arrays::toString).forEach(line -> builder.append(line + System.lineSeparator()));
        return builder.toString();
    }
}
