package org.pitrecki.cipher.interfaces.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Use this annotation to indicates that method is intended to
 * to encryption.
 *
 * @author Piotr 'pitrecki' Nowak
 *         Created by Pitrecki on 2017-02-22.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.SOURCE)
public @interface Encryption
{
}
