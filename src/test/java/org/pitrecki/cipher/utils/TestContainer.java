package org.pitrecki.cipher.utils;

/**
 * Created by Pitrecki on 2016-12-24.
 */
public interface TestContainer
{
    //Text section
    String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    String SHORT_MESSAGE = "STRIKE NOW".replaceAll("[\\W]", "");
    String LONG_MESSAGE = "the gold is buried in orono".replaceAll("[\\W]", "").toUpperCase();

    //Key section


}
