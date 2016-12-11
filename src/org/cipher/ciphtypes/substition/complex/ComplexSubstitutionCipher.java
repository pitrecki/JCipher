package org.cipher.ciphtypes.substition.complex;

import org.cipher.ciphtypes.Cipher;

/**
 * //TODO opisać
 * Withount description
 * @author Piotr 'pitrecki' Nowak
 * @version 0.0.1
 * Created by Pitrecki on 2016-11-04.
 */
public abstract class ComplexSubstitutionCipher extends Cipher
{

    public ComplexSubstitutionCipher() {
        super();
    }

    @Override
    public Character[][] getCryptMatrix() {
        return ((Character[][]) super.getCryptMatrix());
    }

    @Override
    protected void setCryptMatrix(Object[][] cryptMatrix) {
        super.setCryptMatrix(cryptMatrix);
    }


    @Override
    public abstract void encrypt(String inputText);

    @Override
    public abstract void decrypt(String inputText);

    @Override
    public String prepareDataToPrint() {
        return getProcessedText().replace("[", "").replace("]", "").replace(",", "").replace(" ", "");
    }
}
