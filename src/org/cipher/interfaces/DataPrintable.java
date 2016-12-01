package org.cipher.interfaces;

/**
 * Created by Pitrecki on 2016-10-18.
 */
public interface DataPrintable
{
    String prepareDataToPrint();

    default void printProccesedData() {
        System.out.println(prepareDataToPrint());
    }

}
