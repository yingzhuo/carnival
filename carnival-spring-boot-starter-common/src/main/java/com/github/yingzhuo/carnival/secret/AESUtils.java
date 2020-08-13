/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.secret;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * @author 应卓
 * @since 1.0.6
 */
@Deprecated
public final class AESUtils {

    private AESUtils() {
    }

    public static String encrypt(String value, String passphrase) {
        try {
            byte[] initVector = new byte[16];
            SecureRandom.getInstanceStrong().nextBytes(initVector);
            Cipher cipher = getCipher();
            cipher.init(Cipher.ENCRYPT_MODE, getKeySpec(passphrase), new IvParameterSpec(initVector));
            byte[] encrypted = cipher.doFinal(value.getBytes());
            return DatatypeConverter.printBase64Binary(initVector) + DatatypeConverter.printBase64Binary(encrypted);
        } catch (GeneralSecurityException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

    public static String decrypt(String encrypted, String passphrase) {
        try {
            byte[] initVector = DatatypeConverter.parseBase64Binary(encrypted.substring(0, 24));
            Cipher cipher = getCipher();
            cipher.init(Cipher.DECRYPT_MODE, getKeySpec(passphrase), new IvParameterSpec(initVector));
            byte[] original = cipher.doFinal(DatatypeConverter.parseBase64Binary(encrypted.substring(24)));
            return new String(original);
        } catch (GeneralSecurityException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

    private static SecretKeySpec getKeySpec(String passphrase) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        return new SecretKeySpec(digest.digest(passphrase.getBytes(StandardCharsets.UTF_8)), "AES");
    }

    // -----------------------------------------------------------------------------------------------------------------

    private static Cipher getCipher() throws NoSuchPaddingException, NoSuchAlgorithmException {
        return Cipher.getInstance("AES/CBC/PKCS5PADDING");
    }

}
