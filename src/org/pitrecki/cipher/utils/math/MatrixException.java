package org.pitrecki.cipher.utils.math;

/**
 * @author Piotr 'pitrecki' Nowak
 * @version 0.5.5
 * Created by Pitrecki on 2016-11-11.
 */
public class MatrixException extends Exception
{
    public MatrixException(String message) {
        super(message);
    }

    public MatrixException(String message, Throwable cause) {
        super(message, cause);
    }

    public MatrixException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    @Override
    public synchronized Throwable getCause() {
        return super.getCause();
    }
}
