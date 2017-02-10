package org.pitrecki.cipher.utils.typewrappers;

import org.pitrecki.cipher.utils.container.NonFractionalNumberContainer;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author Piotr 'pitrecki' Nowak
 *         Created by Pitrecki on 2017-02-09.
 */
public class AnyNumberArrayConverter<T extends Number, V extends Number>
{
    private Class<V> target;
    private Class<?> base;

    public AnyNumberArrayConverter(Class<V> target) {
        this.target = target;
    }

    /**
     * Convert 2D array to another type
     *
     * @param array of T type
     * @return new 2D array of V type
     */

    @SuppressWarnings("unchecked V cast")
    public V[][] convertArray(T[][] array) {
        base = array.getClass().getComponentType().getComponentType();

        final String methodName = "valueOf";
        final int column = array[0].length;
        final int row = array.length;

        return Arrays.stream(array)
                .map(ts -> Arrays.stream(ts)
                        .map(t -> invokeMethod(t, String.class, methodName, Object.class))
                        .map(e -> removeFractionalPart(e.toString()))
                        .map(o -> invokeMethod(o, target, methodName, o.getClass()))
                        .toArray(value -> ((V[]) Array.newInstance(target, column))))
                .toArray(value -> ((V[][]) Array.newInstance(target, row, column)));
    }


    /**
     * Invoke required method using reflection
     *
     * @param obj method argument
     * @param invokingClass class object which invoking method
     * @param methodName method name to call
     * @param argumentType type of argument
     * @return new processed object
     */

    private Object invokeMethod(Object obj, Class<?> invokingClass, String methodName, Class<?> argumentType) {
        Object o = null;
        try {
            Method method = invokingClass.getDeclaredMethod(methodName, argumentType);
            o = method.invoke(null, obj);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return o;
    }

    /**
     * Remove fractional part if V target is non Double/FLoat type
     * @param str number as string
     * @return converted string
     */

    private String removeFractionalPart(String str) {
        if ((base.getClass().isInstance(Double.class) || base.getClass().isInstance(Float.class)) && NonFractionalNumberContainer.isNumberNotFractional(target)) {
            return str.substring(0, str.indexOf("."));
        }

        return str;
    }
}
