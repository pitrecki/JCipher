package org.pitrecki.cipher.utils.container;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Piotr 'pitrecki' Nowak
 *         Created by Pitrecki on 2017-02-10.
 *
 * Perhaps in nealy future it will refactored by more thoughtful code.
 */
public final class NonFractionalNumberContainer
{
    private static Map<String, Class<?>> nonFractinalMap = null;

    static {
        generateMap();
    }

    private static void generateMap() {
        nonFractinalMap = new HashMap<>();
        nonFractinalMap.put("Integer", Integer.class);
        nonFractinalMap.put("Short", Short.class);
        nonFractinalMap.put("Long", Long.class);
    }

    public static boolean isNumberNotFractional(Class<?> clazz) {
        return nonFractinalMap.containsValue(clazz);
    }
}
