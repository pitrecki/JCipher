package org.cipher.ciphtypes.substition.complex;

import org.cipher.ciphtypes.Cipher;
import org.cipher.utils.math.Matrix;

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
    protected void setCryptMatrix(Matrix cryptMatrix) {
        super.setCryptMatrix(cryptMatrix);
    }


    @Override
    public abstract void encrypt(String inputText);

    @Override
    public abstract void decrypt(String inputText);

}
