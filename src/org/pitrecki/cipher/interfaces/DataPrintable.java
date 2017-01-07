package org.pitrecki.cipher.interfaces;

import org.pitrecki.cipher.ciphtypes.Cipher;

/**
 * This interface provides the basic functionality of printing result on screen.
 * @author Piotr 'pitrecki' Nowak
 * @version 0.6.2
 * @see Cipher
 * Created by Pitrecki on 2016-10-18.
 */
public interface DataPrintable
{
    String prepareDataToPrint();

    default void printProccesedData() {
        System.out.println(prepareDataToPrint());
    }

}
