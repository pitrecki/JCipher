package org.cipher.utils;

/**
 * Created by Pitrecki on 2016-11-26.
 */
public final class Counter
{
    public static int COUNTER = 0;

    private Counter() {

    }

    public static int resetCounter() {
        return COUNTER = 0;
    }

    public static int increaseCounter() {
        return COUNTER++;
    }

    public static int decreaseCounter() {
        return COUNTER--;
    }
}
