package org.pitrecki.cipher.utils;

import java.util.Comparator;

/**
 * Simple custom comparator based on column comparision entered
 * by user.
 *
 * @author Piotr 'pitrecki' Nowak
 *         Created by Pitrecki on 2017-02-21.
 */
public class ColumnArrayComparator implements Comparator<Comparable[]>
{
    private int rowNumber;

    public ColumnArrayComparator(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    @Override
    @SuppressWarnings("unchecked cast")
    public int compare(Comparable[] fArray, Comparable[] sArray) {
        return fArray[rowNumber].compareTo(sArray[rowNumber]);
    }
}
