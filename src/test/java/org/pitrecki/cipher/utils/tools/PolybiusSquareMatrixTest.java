package org.pitrecki.cipher.utils.tools;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.pitrecki.cipher.utils.math.Matrix;

import java.security.InvalidKeyException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

/**
 * @author Piotr 'pitrecki' Nowak
 *         Created by Pitrecki on 2017-02-19.
 */
class PolybiusSquareMatrixTest
{
    private PolybiusSquareMatrix polybiusSquareMatrix;

    @Test
    @DisplayName("Test polybius matrix generator with shorter key")
    void testWithShorterKeyShouldThrowsException() {
        assertThatThrownBy(() -> new PolybiusSquareMatrix("avddasda"))
            .isInstanceOf(InvalidKeyException.class)
            .hasMessageContaining("Invalid cipher key length")
            .hasNoCause();
    }

    @Test
    @DisplayName("Test polybius matrix generator with longer key")
    void testLongerKeyShouldThrowsException() {
        assertThatThrownBy(() -> new PolybiusSquareMatrix("avddasdaavddasdaavddasdaavddasda"))
                .isInstanceOf(InvalidKeyException.class)
                .hasMessageContaining("Invalid cipher key length")
                .hasNoCause();
    }

    @Test
    @DisplayName("Test polybius matrix generator with not unique key")
    void testNotUniqueKeyShouldThrowsException() {
        assertThatThrownBy(() -> new PolybiusSquareMatrix("phqgiumeaylnofdxkrcvstzwa"))
                .isInstanceOf(InvalidKeyException.class)
                .hasMessageContaining("Cipher key is not unique")
                .hasNoCause();
    }

    @Test
    @DisplayName("Test polybius matrix generator with key which contains numbers")
    void testKeyWhichContainsNumberShouldThrowsException() {
        assertSoftly(softly -> {
            assertThatThrownBy(() -> new PolybiusSquareMatrix("0phqgiumeaylnofdxkrcvstzw"))
                    .isInstanceOf(InvalidKeyException.class)
                    .hasMessageContaining("Key contains illegal character")
                    .hasNoCause();

            assertThatThrownBy(() -> new PolybiusSquareMatrix("phqgiumeaylnofdxkrcvstzw0"))
                    .isInstanceOf(InvalidKeyException.class)
                    .hasMessageContaining("Key contains illegal character")
                    .hasNoCause();

            assertThatThrownBy(() -> new PolybiusSquareMatrix("phqgiumeaylno9fdxkrcvstzw"))
                    .isInstanceOf(InvalidKeyException.class)
                    .hasMessageContaining("Key contains illegal character")
                    .hasNoCause();
        });
    }

    @Test
    @DisplayName("Test polybius matrix generator with key which contains J letter")
    void testKeyWithJLetterShouldThrowsException() {
        assertThatThrownBy(() -> new PolybiusSquareMatrix("phqgiumeaylnofdxkrJcvstzw"))
                .isInstanceOf(InvalidKeyException.class)
                .hasMessageContaining("Key contains illegal character")
                .hasNoCause();
    }

    @Test
    @DisplayName("Test polybius matrix generator with random key")
    void testRandomKeyMatrix() {
        polybiusSquareMatrix = new PolybiusSquareMatrix();
        Matrix<Character> actual = polybiusSquareMatrix.getPolybiusMatrix();

        assertSoftly(softly -> {
            assertThat(actual).isInstanceOf(Matrix.class);
            assertThat(actual.getData()).isInstanceOf(Character[][].class);
            assertThat(actual.getData()).hasSize(5).hasSize(5);
            assertThat(actual.getData()).doesNotContain(new Character[]{'J', 'j'});
        });
    }
}