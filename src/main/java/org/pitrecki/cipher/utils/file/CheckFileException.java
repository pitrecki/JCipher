package org.pitrecki.cipher.utils.file;

import java.io.IOException;

/**
 * @author Piotr 'pitrecki' Nowak
 *
 * Created by Pitrecki on 2016-10-22.
 */
public class CheckFileException extends IOException
{
    public CheckFileException() {
        super();
    }

    public CheckFileException(String message) {
        super(message);
    }

    public CheckFileException(String message, Throwable cause) {
        super(message, cause);
    }
}
