package org.pitrecki.cipher.utils;

/**
 * Varration of quicksort, no longer support for this piece of code
 *
 * @author Piotr 'pitrecki' Nowak
 *         Created by Pitrecki on 2017-02-21.
 */
@Deprecated
public class _ColumnArrayComparator
{
    public _ColumnArrayComparator() {
    }

    public void sort(Character[] x0, Character[] x1, int left, int right) {
        int index = partition(x0, x1, left, right);
        if (left < index - 1)
            sort(x0, x1, left, index - 1);
        if (index < right)
            sort(x0, x1, index, right);
    }

    private int partition(Character[] x0, Character[] x1, int left, int right) {
        int i = left;
        int j = right;
        int pivot = x0[(left + right) / 2];
        char tmp;
        while (i <= j) {
            while (x0[i] < pivot)
                i++;
            while (x0[j] > pivot)
                j--;
            if (i <= j) {
                //swaping x0
                tmp = x0[i];
                x0[i] = x0[j];
                x0[j] = tmp;

                //swaping x1
                tmp = x1[i];
                x1[i] = x1[j];
                x1[j] = tmp;

                i++;
                j--;
            }
        }

        return i;
    }
}
