/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.security.jwt.signature.support;

import com.github.yingzhuo.carnival.restful.security.jwt.signature.AlgorithmFactory;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * @author 应卓
 * @since 1.6.0
 */
public abstract class AbstractRSAAlgorithmFactory implements AlgorithmFactory {

    private final static String RSA = "RSA";

    private byte[] decryptBase64(String key) {
        return Base64.getDecoder().decode(key);
    }

    protected RSAPublicKey toPublicKey(String key) {
        try {
            byte[] bytes = decryptBase64(key);
            X509EncodedKeySpec spec = new X509EncodedKeySpec(bytes);
            KeyFactory factory = KeyFactory.getInstance(RSA);
            java.security.PublicKey result = factory.generatePublic(spec);
            return (RSAPublicKey) result;
        } catch (NoSuchAlgorithmException e) {
            throw new AssertionError();
        } catch (InvalidKeySpecException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

    protected RSAPrivateKey toPrivateKey(String key) {
        try {
            byte[] bytes = decryptBase64(key);
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(bytes);
            KeyFactory factory = KeyFactory.getInstance(RSA);
            java.security.PrivateKey result = factory.generatePrivate(spec);
            return (RSAPrivateKey) result;
        } catch (NoSuchAlgorithmException e) {
            throw new AssertionError();
        } catch (InvalidKeySpecException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

}
