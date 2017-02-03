package org.pitrecki.cipher.utils.typewrappers;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Piotr 'pitrecki' Nowak
 *         Created by Pitrecki on 2017-02-02.
 */
public class TypeWrapper
{
    private Class<?> clazz;
    private Map<String, Class<?>> mapOfPrimitives;

    public TypeWrapper(Class<?> clazz) {
        this.clazz = clazz;
        this.mapOfPrimitives = generateMapOfPrimitives();
    }

    /**
     * Generate map with primitive types
     */

    private static Map<String, Class<?>> generateMapOfPrimitives() {
        Map<String, Class<?>> map = new HashMap<>();
        map.put("boolean", Boolean.TYPE);
        map.put("double", Double.TYPE);
        map.put("int", Integer.TYPE);
        map.put("long", Long.TYPE);
        map.put("short", Short.TYPE);
        map.put("char", Character.TYPE);
        return map;
    }

    /**
     * Check if type contains own primitive type
     *
     * @return true if connstains
     *         false if not
     */
    public boolean havePrimitiveType() {
        return mapOfPrimitives.containsKey(clazz.getSimpleName().toLowerCase());
    }

    /**
     * Check if type is primitive
     * @return true if is
     *         false if not
     */
    public boolean isPrimitiveType() {
        return clazz.isPrimitive();
    }
}
