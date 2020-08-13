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
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author 应卓
 * @since 1.6.1
 */
public final class RSAHelper extends AbstractSecuritySupport {

    private final String base64PublicKey;
    private final String base64PrivateKey;

    private RSAHelper(RSAKeyPair pair) {
        this.base64PublicKey = pair.getBase64PublicKey();
        this.base64PrivateKey = pair.getBase64PrivateKey();
    }

    public static RSAHelper of(RSAKeyPair pair) {
        return new RSAHelper(pair);
    }

    public String encryptByPublicKey(String encryptingString) {
        try {
            byte[] publicKeyBytes = decryptBase64(base64PublicKey);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
            byte[] data = encryptingString.getBytes(StandardCharsets.UTF_8);
            KeyFactory factory;
            factory = KeyFactory.getInstance(RSA);
            PublicKey publicKey = factory.generatePublic(keySpec);
            Cipher cipher = Cipher.getInstance(factory.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            return encryptBase64(cipher.doFinal(data));
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

    public String decryptByPrivateKey(String encryptedString) {
        try {
            byte[] privateKeyBytes = decryptBase64(base64PrivateKey);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            byte[] data = decryptBase64(encryptedString);
            KeyFactory factory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = factory.generatePrivate(keySpec);
            Cipher cipher = Cipher.getInstance(factory.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return new String(cipher.doFinal(data), StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

    public String encryptByPrivateKey(String encryptingString) {
        try {
            byte[] privateKeyBytes = decryptBase64(base64PrivateKey);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            byte[] data = encryptingString.getBytes(StandardCharsets.UTF_8);
            KeyFactory factory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = factory.generatePrivate(keySpec);
            Cipher cipher = Cipher.getInstance(factory.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            return encryptBase64(cipher.doFinal(data));
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

    public String decryptByPublicKey(String encryptedString) {
        try {
            byte[] publicKeyBytes = decryptBase64(base64PublicKey);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
            byte[] data = decryptBase64(encryptedString);
            KeyFactory factory = KeyFactory.getInstance("RSA");
            PublicKey publicKey = factory.generatePublic(keySpec);
            Cipher cipher = Cipher.getInstance(factory.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            return new String(cipher.doFinal(data), StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

    public String sign(String string) {
        try {
            byte[] data = string.getBytes();
            byte[] bytes = decryptBase64(base64PrivateKey);
            PKCS8EncodedKeySpec pkcs = new PKCS8EncodedKeySpec(bytes);
            KeyFactory factory = KeyFactory.getInstance(RSA);
            PrivateKey key = factory.generatePrivate(pkcs);
            Signature signature = Signature.getInstance(MD5withRSA);
            signature.initSign(key);
            signature.update(data);
            return encryptBase64(signature.sign());
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

    public boolean verify(String string, String sign) {
        try {
            byte[] data = string.getBytes();
            byte[] bytes = decryptBase64(base64PublicKey);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(bytes);
            KeyFactory factory = KeyFactory.getInstance(RSA);
            PublicKey key = factory.generatePublic(keySpec);
            Signature signature = Signature.getInstance(MD5withRSA);
            signature.initVerify(key);
            signature.update(data);
            return signature.verify(decryptBase64(sign));
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

}
