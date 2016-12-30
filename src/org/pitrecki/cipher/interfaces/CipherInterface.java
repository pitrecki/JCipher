package org.pitrecki.cipher.interfaces;

import org.pitrecki.cipher.ciphtypes.Cipher;

/**
 * This interface provides the basic functionality of the encryption and decryption
 *
 * @author Piotr 'pitrecki' Nowak
 * @version 0.5.8
 *
 * @see Cipher
 * Created by Pitrecki on 2016-10-15.
 */
public interface CipherInterface
{
    /**
     * In cryptography, encryption is the process of encoding messages or information
     * in such a way that only authorized parties can access it. Encryption does not of
     * itself prevent interference, but denies the message content to the interceptor.
     * In an encryption scheme, the intended communication information or message, referred
     * to as plaintext, is encrypted using an encryption algorithm, generating ciphertext
     * that can only be read if decrypted. For technical reasons, an encryption scheme usually
     * uses a pseudo-random encryption key generated by an algorithm. It is in principle possible
     * to decrypt the message without possessing the key, but, for a well-designed encryption
     * scheme,large computational resources and skill are required. An authorized recipient
     * can easily decrypt the message with the key provided by the originator to recipients,
     * but not tounauthorized interceptors. The purpose of encryption is to ensure that only
     * somebodywho is authorized to access data (e.g. a text message or a file), will be able
     * toread it, using the decryption key. Somebody who is not authorized can be excluded, because
     * he or she does not have the required key, without which it is impossibleto read the encrypted
     * information.
     *
     * @param inputText text to encrypt
     */
    void encrypt(String inputText);

    /**
     *  Decryption is the reverse, in other words, moving from the unintelligible ciphertext back to
     *  plaintext. A cipher (or cypher) is a pair of algorithms that create the encryption and the
     *  reversing decryption. The detailed operation of a cipher is controlled both by the algorithm
     *  and in each instance by a "key". The key is a secret (ideally known only to the communicants),
     *  usually a short string of characters, which is needed to decrypt the ciphertext.
     *
     * @param inputText text to decrypt
     */
    void decrypt(String inputText);

}
