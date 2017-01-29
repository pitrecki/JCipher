package org.pitrecki.cipher.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

/**
 * Created by Pitrecki on 2016-12-26.
 */
class EncryptMatrixGeneratorTest
{
    private EncryptMatrixGenerator<Object> encryptMatrixGenerator;

    @Test
    @DisplayName("Testing of crypt matrix object instantion")
    void testOfCratingCryptMatrixInstantion() {
        encryptMatrixGenerator = new EncryptMatrixGenerator.EncryptMatrixGeneratorBuilder<>(Object.class).build();
        assertThat(encryptMatrixGenerator).isNotNull();

    }

    @Test
    @DisplayName("Testing of valid size of encrypt matrix")
    void testOfSetSize() {
        encryptMatrixGenerator = new EncryptMatrixGenerator.EncryptMatrixGeneratorBuilder<>(Object.class).withSize(10).build();
        int expectedRowNumber = 10;
        int expectedColumnNumber = 10;

        int actualRowNumber = encryptMatrixGenerator.getMatrix().getRow();
        int actualColumnNumber = encryptMatrixGenerator.getMatrix().getColumn();

        assertSoftly(softly-> {
            assertThat(expectedRowNumber).isEqualTo(actualRowNumber);
            assertThat(expectedColumnNumber).isEqualTo(actualColumnNumber);
        });
    }

    @Test
    @DisplayName("Testing of valid column size of encrypt matrix")
    void testOfSetColumn() {
        encryptMatrixGenerator = new EncryptMatrixGenerator.EncryptMatrixGeneratorBuilder<>(Object.class).withColumn(3).build();
        int expectedRowNumber = 5;
        int expectedColumnNumber = 3;

        int actualRowNumber = encryptMatrixGenerator.getMatrix().getRow();
        int actualColumnNumber = encryptMatrixGenerator.getMatrix().getColumn();

        assertSoftly(softly-> {
            assertThat(expectedRowNumber).isEqualTo(actualRowNumber);
            assertThat(expectedColumnNumber).isEqualTo(actualColumnNumber);
        });
    }

    @Test
    @DisplayName("Testing of valid row size of encrypt matrix")
    void testOfSetRow() {
        encryptMatrixGenerator = new EncryptMatrixGenerator.EncryptMatrixGeneratorBuilder<>(Object.class).withRow(2).build();
        int expectedRowNumber = 2;
        int expectedColumnNumber = 5;

        int actualRowNumber = encryptMatrixGenerator.getMatrix().getRow();
        int actualColumnNumber = encryptMatrixGenerator.getMatrix().getColumn();

        assertSoftly(softly -> {
            assertThat(expectedRowNumber).isEqualTo(actualRowNumber);
            assertThat(expectedColumnNumber).isEqualTo(actualColumnNumber);
        });

    }

    @Test
    @DisplayName("Testing of throwing IllegalArgumentException, when fist argument is null")
    void testOfThrowingExpectionWhenClassTArgumentIsNull() {
        assertThatThrownBy(() ->  new EncryptMatrixGenerator.EncryptMatrixGeneratorBuilder<>(null).build())
                .isInstanceOf(IllegalArgumentException.class)
                .hasNoCause();
    }

    @Test
    @DisplayName("Testing of throwing IllegalArgumentException, when size is negative")
    void testOfThrowingExpectionWhenSizeIsNegative() {
        assertThatThrownBy(() -> new EncryptMatrixGenerator.EncryptMatrixGeneratorBuilder<>(Object.class).withSize(-1).build())
                .isInstanceOf(IllegalArgumentException.class)
                .hasNoCause();

    }

    @Test
    @DisplayName("Testing of throwing IllegalArgumentException, when column size is argument is negative")
    void testOfThrowingExpectionWhenColumnIsNegative() {
        assertThatThrownBy(() -> new EncryptMatrixGenerator.EncryptMatrixGeneratorBuilder<>(Object.class).withColumn(-5).build())
                .isInstanceOf(IllegalArgumentException.class)
                .hasNoCause();
    }

    @Test
    @DisplayName("Testing of throwing IllegalArgumentException,  when row size is argument is negative")
    void testOfThrowingExpectionWhenRowSizeIsNegative() {
        assertThatThrownBy(() -> new EncryptMatrixGenerator.EncryptMatrixGeneratorBuilder<>(Object.class).withRow(-1).build())
                .isInstanceOf(IllegalArgumentException.class)
                .hasNoCause();
    }

    @Test
    @DisplayName("Testing if encrypt matrix is filled properly")
    void testOfCryptMatrixIsFilledProperly() {
        Integer[] valuesToFillMatrix = {2, 2, -3, -1};

        encryptMatrixGenerator = new EncryptMatrixGenerator.EncryptMatrixGeneratorBuilder<>(Integer.class).withSize(2).build();
        encryptMatrixGenerator.fill(valuesToFillMatrix);

        String expectedMatrix = "[2, 2]\r\n" +
                                "[-3, -1]\r\n";

        String actualMatrix = encryptMatrixGenerator.getMatrix().toString();

        assertThat(actualMatrix).isEqualTo(expectedMatrix);
    }
}