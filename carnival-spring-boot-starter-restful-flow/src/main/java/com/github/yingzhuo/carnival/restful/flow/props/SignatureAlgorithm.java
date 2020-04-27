/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.flow.props;

import com.auth0.jwt.algorithms.Algorithm;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/**
 * @author 应卓
 */
public enum SignatureAlgorithm {

    HMAC256,

    HMAC384,

    HMAC512,

    RSA256,

    RSA384,

    RSA512;

    /* 内部使用 */
    public Algorithm toJwtAlgorithm(String secret) {
        KeyPairGenerator generator;
        KeyPair keyPair;
        RSAPublicKey publicKey;
        RSAPrivateKey privateKey;

        try {
            switch (this) {
                // TODO: RSA 算法在节点多开时有严重问题
                case RSA256:
                    generator = KeyPairGenerator.getInstance("RSA");
                    generator.initialize(1024);
                    keyPair = generator.generateKeyPair();
                    publicKey = (RSAPublicKey) keyPair.getPublic();
                    privateKey = (RSAPrivateKey) keyPair.getPrivate();
                    return Algorithm.RSA256(publicKey, privateKey);
                // TODO: RSA 算法在节点多开时有严重问题
                case RSA384:
                    generator = KeyPairGenerator.getInstance("RSA");
                    generator.initialize(1024);
                    keyPair = generator.generateKeyPair();
                    publicKey = (RSAPublicKey) keyPair.getPublic();
                    privateKey = (RSAPrivateKey) keyPair.getPrivate();
                    return Algorithm.RSA384(publicKey, privateKey);
                // TODO: RSA 算法在节点多开时有严重问题
                case RSA512:
                    generator = KeyPairGenerator.getInstance("RSA");
                    generator.initialize(1024);
                    keyPair = generator.generateKeyPair();
                    publicKey = (RSAPublicKey) keyPair.getPublic();
                    privateKey = (RSAPrivateKey) keyPair.getPrivate();
                    return Algorithm.RSA512(publicKey, privateKey);
                case HMAC256:
                    return Algorithm.HMAC256(secret);
                case HMAC384:
                    return Algorithm.HMAC384(secret);
                case HMAC512:
                    return Algorithm.HMAC512(secret);
                default:
                    throw new IllegalArgumentException();
            }
        } catch (NoSuchAlgorithmException e) {
            throw new AssertionError();
        }
    }

}
