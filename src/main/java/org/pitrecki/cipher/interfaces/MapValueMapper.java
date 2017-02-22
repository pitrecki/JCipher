package org.pitrecki.cipher.interfaces;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Mapping value within the map and return the key value.
 *
 * For more info please look here:
 * <a href=http://stackoverflow.com/questions/1383797/java-hashmap-how-to-get-key-from-value>LINK</a>
 *
 * @author Piotr 'pitrecki' Nowak
 *         Created by Pitrecki on 2017-02-21.
 */
public interface MapValueMapper<K, V>
{
    /**
     * Search the map to find key after pass a value from map
     *
     * @param map which will be searched
     * @param value in the map
     * @return key
     */

    default Set<K> getKeyByValue(Map<K, V> map, V value) {
        return map.entrySet().stream()
                .filter(entry -> Objects.equals(entry.getValue(), value))
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }
}
