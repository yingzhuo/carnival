/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.common.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.spec.KeySpec;

/**
 * @author 应卓
 */
public final class AESEncrypter {

    private static final byte[] SALT = {
            (byte) 0xA9, (byte) 0x9B, (byte) 0xC8, (byte) 0x32,
            (byte) 0x56, (byte) 0x35, (byte) 0xE3, (byte) 0x03
    };

    private static final int ITERATION_COUNT = 65536;
    private static final int KEY_LENGTH = 256;
    private final Cipher ecipher;
    private final Cipher dcipher;

    public AESEncrypter(String passPhrase) {
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            KeySpec spec = new PBEKeySpec(passPhrase.toCharArray(), SALT, ITERATION_COUNT, KEY_LENGTH);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKey secret = new SecretKeySpec(tmp.getEncoded(), "AES");

            ecipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            ecipher.init(Cipher.ENCRYPT_MODE, secret);

            dcipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            byte[] iv = ecipher.getParameters().getParameterSpec(IvParameterSpec.class).getIV();
            dcipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(iv));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public String encrypt(String encrypt) {
        try {
            byte[] bytes = encrypt.getBytes("UTF8");
            byte[] encrypted = encrypt(bytes);
            return new BASE64Encoder().encode(encrypted);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public byte[] encrypt(byte[] plain){
        try {
            return ecipher.doFinal(plain);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public String decrypt(String encrypt) {
        try {
            byte[] bytes = new BASE64Decoder().decodeBuffer(encrypt);
            byte[] decrypted = decrypt(bytes);
            return new String(decrypted, "UTF8");
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public byte[] decrypt(byte[] encrypt) {
        try {
            return dcipher.doFinal(encrypt);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

}
