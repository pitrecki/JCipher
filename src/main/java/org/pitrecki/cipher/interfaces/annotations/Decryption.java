package org.pitrecki.cipher.interfaces.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Piotr 'pitrecki' Nowak
 *         Created by Pitrecki on 2017-02-22.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.CLASS)
public @interface Decryption
{
}
