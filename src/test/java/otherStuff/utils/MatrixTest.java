package otherStuff.utils;

import org.pitrecki.cipher.utils.math.Matrix;
import org.pitrecki.cipher.utils.math.MatrixException;

/**
 * Created by Pitrecki on 2016-11-11.
 */
public class MatrixTest
{
    public static void main(String[] args) throws MatrixException {
        Matrix matrix = new Matrix(3);
        Integer[][] ints = new Integer[][] {{1, 2, 3}};
        matrix = null;

        matrix = new Matrix(ints).transpose();


        Matrix A = new Matrix(new Integer[][]{{12, 5}, {7, 1}});
        Matrix B = new Matrix(new Integer[][] {{2, 1}}).transpose();
        try {
            A.multiply(B);
//            System.out.println(B.printData());
        } catch (MatrixException e) {
            e.printStackTrace();
        }


//        Matrix C = new Matrix(new Double[][]{{5d, 17d, 1d}, {4d, 15d, 1d}, {1d, 1d, 1d}});
        Matrix C = new Matrix(new Integer[][] {{5, 17}, {4, 15}});
        double det = C.determinant(C);
        C = C.inverse();
        Matrix testKey = new Matrix(new Double[][]{{5d, 17d}, {4d, 15d}}).adjugate();
        System.out.print("");
//        C.convertDoubleDataToInteger();
        System.out.println(C.toString());

    }
}
