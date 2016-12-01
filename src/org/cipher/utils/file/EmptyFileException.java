package org.cipher.utils.file;

import java.io.IOException;

/**
 * Created by Pitrecki on 2016-10-22.
 */
public class EmptyFileException extends IOException
{
    public EmptyFileException() {
        super();
    }

    public EmptyFileException(String message) {
        super(message);
    }

    public EmptyFileException(String message, Throwable cause) {
        super(message, cause);
    }
}
