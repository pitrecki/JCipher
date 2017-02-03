package org.pitrecki.cipher.utils.typewrappers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

/**
 * @author Piotr 'pitrecki' Nowak
 *         Created by Pitrecki on 2017-02-03.
 */
class TypeWrapperTest
{
    private TypeWrapper typeWrapper;

    @Test
    @DisplayName("Test primitive type is primitive")
    void testOfPrimitiveTypeShouldReturnTrue() {
        typeWrapper = new TypeWrapper(int.class);

        boolean actual = typeWrapper.isPrimitiveType();
        assertThat(actual).isTrue();
    }

    @Test
    @DisplayName("Test non primitve type is not primitive")
    void testOFNotPrimitiveTypeShouldReturnFalse() {
        typeWrapper = new TypeWrapper(Integer.class);

        boolean actual = typeWrapper.isPrimitiveType();
        assertThat(actual).isFalse();
    }

    @Test
    @DisplayName("Test object which have own primitive type")
    void testTypeWithPrimitiveTypeShouldReturnTrue() {
        typeWrapper = new TypeWrapper(Double.class);

        boolean actual = typeWrapper.havePrimitiveType();
        assertThat(actual).isTrue();
    }

    @Test
    @DisplayName("Test object which not have primitive type")
    void testTypeWithoutPrimitiveTypeShouldReturnFalse() {
        typeWrapper = new TypeWrapper(ArrayList.class);

        boolean actual = typeWrapper.havePrimitiveType();
        assertThat(actual).isFalse();
    }

    @Test
    void testMapOfPrimitives() throws NoSuchFieldException, IllegalAccessException {
        typeWrapper = new TypeWrapper(Object.class);

        Field f = typeWrapper.getClass().getDeclaredField("mapOfPrimitives");
        f.setAccessible(true);
        Map<String, Class<?>> mapObtainByReflection = (Map<String, Class<?>>) f.get(typeWrapper);

        assertSoftly(softly -> {
            assertThat(mapObtainByReflection).containsOnlyKeys("boolean", "double", "int", "long", "short", "char");
            assertThat(mapObtainByReflection).containsEntry("boolean", Boolean.TYPE);
            assertThat(mapObtainByReflection).containsValues(Character.TYPE);
            assertThat(mapObtainByReflection).doesNotContainKey("Integer");
            assertThat(mapObtainByReflection).doesNotContainEntry("Integer", Integer.class);
            assertThat(mapObtainByReflection).doesNotContainValue(Integer.class);
        });
    }
}