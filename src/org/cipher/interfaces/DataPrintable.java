package org.cipher.interfaces;

/**
 * This interface provides the basic functionality of printing result on screen.
 * @author Piotr 'pitrecki' Nowak
 * @version 1.0.0
 * @see org.cipher.ciphtypes.Cipher
 * Created by Pitrecki on 2016-10-18.
 */
public interface DataPrintable
{
    String prepareDataToPrint();

    default void printProccesedData() {
        System.out.println(prepareDataToPrint());
    }

}
