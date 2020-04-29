/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.security.jwt.signature;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * @author 应卓
 * @since 1.6.0
 */
abstract class AbstractECDSAAlgorithmFactory implements AlgorithmFactory {

    private static final String EC = "EC";

    private byte[] decryptBase64(String key) {
        return Base64.getDecoder().decode(key);
    }

    protected ECPublicKey toPublicKey(String key) {
        try {
            byte[] bytes = decryptBase64(key);
            X509EncodedKeySpec spec = new X509EncodedKeySpec(bytes);
            KeyFactory factory = KeyFactory.getInstance(EC);
            java.security.PublicKey result = factory.generatePublic(spec);
            return (ECPublicKey) result;
        } catch (NoSuchAlgorithmException e) {
            throw new AssertionError();
        } catch (InvalidKeySpecException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

    protected ECPrivateKey toPrivateKey(String key) {
        try {
            byte[] bytes = decryptBase64(key);
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(bytes);
            KeyFactory factory = KeyFactory.getInstance(EC);
            java.security.PrivateKey result = factory.generatePrivate(spec);
            return (ECPrivateKey) result;
        } catch (NoSuchAlgorithmException e) {
            throw new AssertionError();
        } catch (InvalidKeySpecException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }
}
