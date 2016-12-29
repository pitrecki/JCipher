package org.pitrecki.cipher.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Pitrecki on 2016-12-26.
 */
class CryptMatrixGeneratorTest
{
    private CryptMatrixGenerator<Object> cryptMatrixGenerator;

    @Test
    @DisplayName("Testing of crypt matrix object instantion")
    void testOfCratingCryptMatrixInstantion() {
        cryptMatrixGenerator = new CryptMatrixGenerator.CryptMatrixGeneratorBuilder<>(Object.class).build();
        assertNotNull(cryptMatrixGenerator);
    }

    @Test
    @DisplayName("Testing of valid size of encrypt matrix")
    void testOfSetSize() {
        cryptMatrixGenerator = new CryptMatrixGenerator.CryptMatrixGeneratorBuilder<>(Object.class).withSize(10).build();
        int expectedRowNumber = 10;
        int expectedColumnNumber = 10;

        int actualRowNumber = cryptMatrixGenerator.getGenereratedCryptMatrix().getRow();
        int actualColumnNumber = cryptMatrixGenerator.getGenereratedCryptMatrix().getColumn();

        assertAll("Testing of dimension", () -> {
            assertEquals(expectedRowNumber, actualRowNumber);
            assertEquals(expectedColumnNumber, actualColumnNumber);
        });
    }

    @Test
    @DisplayName("Testing of valid column size of encrypt matrix")
    void testOfSetColumn() {
        cryptMatrixGenerator = new CryptMatrixGenerator.CryptMatrixGeneratorBuilder<>(Object.class).withColumn(3).build();
        int expectedRowNumber = 5;
        int expectedColumnNumber = 3;

        int actualRowNumber = cryptMatrixGenerator.getGenereratedCryptMatrix().getRow();
        int actualColumnNumber = cryptMatrixGenerator.getGenereratedCryptMatrix().getColumn();

        assertAll("Testing of dimension", () -> {
            assertEquals(expectedRowNumber, actualRowNumber);
            assertEquals(expectedColumnNumber, actualColumnNumber);
        });
    }

    @Test
    @DisplayName("Testing of valid row size of encrypt matrix")
    void testOfSetRow() {
        cryptMatrixGenerator = new CryptMatrixGenerator.CryptMatrixGeneratorBuilder<>(Object.class).withRow(2).build();
        int expectedRowNumber = 2;
        int expectedColumnNumber = 5;

        int actualRowNumber = cryptMatrixGenerator.getGenereratedCryptMatrix().getRow();
        int actualColumnNumber = cryptMatrixGenerator.getGenereratedCryptMatrix().getColumn();

        assertAll("Testing of dimension", () -> {
            assertEquals(expectedRowNumber, actualRowNumber);
            assertEquals(expectedColumnNumber, actualColumnNumber);
        });
    }

    @Test
    @DisplayName("Testing of throwing IllegalArgumentException, when fist argument is null")
    void testOfThrowingExpectionWhenClassTArgumentIsNull() {
        assertThrows(IllegalArgumentException.class, () ->
                new CryptMatrixGenerator.CryptMatrixGeneratorBuilder<>(null).build());
    }

    @Test
    @DisplayName("Testing of throwing IllegalArgumentException, when size is negative")
    void testOfThrowingExpectionWhenSizeIsNegative() {
        assertThrows(IllegalArgumentException.class, () ->
                new CryptMatrixGenerator.CryptMatrixGeneratorBuilder<>(Object.class).withSize(-1).build());
    }

    @Test
    @DisplayName("Testing of throwing IllegalArgumentException, when column size is argument is negative")
    void testOfThrowingExpectionWhenColumnIsNegative() {
        assertThrows(IllegalArgumentException.class, () ->
                new CryptMatrixGenerator.CryptMatrixGeneratorBuilder<>(Object.class).withColumn(-5).build());
    }

    @Test
    @DisplayName("Testing of throwing IllegalArgumentException,  when row size is argument is negative")
    void testOfThrowingExpectionWhenRowSizeIsNegative() {
        assertThrows(IllegalArgumentException.class, () ->
                new CryptMatrixGenerator.CryptMatrixGeneratorBuilder<>(Object.class).withRow(-1).build());
    }

    @Test
    @DisplayName("Testing if encrypt matrix is filled properly")
    void testOfCryptMatrixIsFilledProperly() {
        Integer[] valuesToFillMatrix = {2, 2, -3, -1};

        cryptMatrixGenerator = new CryptMatrixGenerator.CryptMatrixGeneratorBuilder<>(Integer.class).withSize(2).build();
        cryptMatrixGenerator.fill(valuesToFillMatrix);

        String expectedMatrix = "[2, 2]\r\n" +
                                "[-3, -1]\r\n";

        String actualMatrix = cryptMatrixGenerator.getGenereratedCryptMatrix().toString();

        assertEquals(expectedMatrix, actualMatrix);
    }
}