package org.cipher.utils.math;

/**
 * Created by Pitrecki on 2016-11-11.
 */
public class InvalidMatrixException extends Exception
{
    public InvalidMatrixException(String message) {
        super(message);
    }

    public InvalidMatrixException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidMatrixException(Throwable cause) {
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
