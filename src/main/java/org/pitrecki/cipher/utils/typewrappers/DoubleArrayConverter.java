package org.pitrecki.cipher.utils.typewrappers;

import java.util.Arrays;

/**
 * @author Piotr 'pitrecki' Nowak
 *         Created by Pitrecki on 2017-02-02.
 */
public class DoubleArrayConverter
{
    public DoubleArrayConverter() {
    }

    public double[][] primitiveDoubleArrayConvert(Double[][] array) {
        return Arrays.stream(array)
                    .map(doubles -> Arrays.stream(doubles)
                            .mapToDouble(Double::doubleValue)
                            .toArray())
                    .toArray(double[][]::new);
    }

    public Double[][] objectDoubleArrayConvert(double[][] array) {
        return Arrays.stream(array)
                    .map(doubles -> Arrays.stream(doubles)
                            .boxed()
                            .toArray(Double[]::new))
                    .toArray(Double[][]::new);
    }

}
