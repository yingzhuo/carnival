/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.security.jwt.util;

import com.auth0.jwt.algorithms.Algorithm;
import com.github.yingzhuo.carnival.restful.security.jwt.SignatureAlgorithm;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Objects;

/**
 * 内部使用工具
 *
 * @author 应卓
 */
public final class InternalUtils {

    private InternalUtils() {
    }

    public static Algorithm toAlgorithm(SignatureAlgorithm signatureAlgorithm, String secret) {
        Objects.requireNonNull(secret);
        Objects.requireNonNull(signatureAlgorithm);

        KeyPairGenerator generator;
        KeyPair keyPair;
        RSAPublicKey publicKey;
        RSAPrivateKey privateKey;

        try {
            switch (signatureAlgorithm) {
                case HMAC256:
                    return Algorithm.HMAC256(secret);
                case HMAC384:
                    return Algorithm.HMAC384(secret);
                case HMAC512:
                    return Algorithm.HMAC512(secret);
                case RSA256:
                    generator = KeyPairGenerator.getInstance("RSA");
                    generator.initialize(1024);
                    keyPair = generator.generateKeyPair();
                    publicKey = (RSAPublicKey) keyPair.getPublic();
                    privateKey = (RSAPrivateKey) keyPair.getPrivate();
                    return Algorithm.RSA256(publicKey, privateKey);
                case RSA384:
                    generator = KeyPairGenerator.getInstance("RSA");
                    generator.initialize(1024);
                    keyPair = generator.generateKeyPair();
                    publicKey = (RSAPublicKey) keyPair.getPublic();
                    privateKey = (RSAPrivateKey) keyPair.getPrivate();
                    return Algorithm.RSA384(publicKey, privateKey);
                case RSA512:
                    generator = KeyPairGenerator.getInstance("RSA");
                    generator.initialize(1024);
                    keyPair = generator.generateKeyPair();
                    publicKey = (RSAPublicKey) keyPair.getPublic();
                    privateKey = (RSAPrivateKey) keyPair.getPrivate();
                    return Algorithm.RSA512(publicKey, privateKey);
                default:
                    throw new IllegalArgumentException();
            }
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

}
