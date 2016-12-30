package org.pitrecki.cipher.ciphtypes.substition.complex;

import org.pitrecki.cipher.ciphtypes.Cipher;
import org.pitrecki.cipher.utils.math.Matrix;

/**
 * Probably it will be delete in future.
 * @author Piotr 'pitrecki' Nowak
 * @version 0.0.1
 * Created by Pitrecki on 2016-11-04.
 */
@Deprecated
abstract class ComplexSubstitutionCipher extends Cipher
{

    public ComplexSubstitutionCipher() {
        super();
    }

    @Override
    protected void setEncryptMatrix(Matrix encryptMatrix) {
        super.setEncryptMatrix(encryptMatrix);
    }


    @Override
    public abstract void encrypt(String inputText);

    @Override
    public abstract void decrypt(String inputText);

}
