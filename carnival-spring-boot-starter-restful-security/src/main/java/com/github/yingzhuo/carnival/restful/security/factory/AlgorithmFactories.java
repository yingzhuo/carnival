/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.security.factory;

import com.auth0.jwt.algorithms.Algorithm;
import com.github.yingzhuo.carnival.common.io.ResourceText;

import java.util.UUID;

/**
 * @author 应卓
 * @since 1.6.1
 */
public final class AlgorithmFactories {

    // -----------------------------------------------------------------------------------------------------------------
    private AlgorithmFactories() {
    }

    // None
    // -----------------------------------------------------------------------------------------------------------------
    public static AlgorithmFactory none() {
        return Algorithm::none;
    }

    // HMAC
    // -----------------------------------------------------------------------------------------------------------------
    public static AlgorithmFactory hmac256(String secret) {
        return () -> Algorithm.HMAC256(secret);
    }

    public static AlgorithmFactory hmac256(ResourceText secret) {
        return () -> Algorithm.HMAC256(secret.getTextAsOneLineAndTrim());
    }

    public static AlgorithmFactory hmac256WithRandomSecret() {
        return hmac256(UUID.randomUUID().toString());
    }

    public static AlgorithmFactory hmac384(String secret) {
        return () -> Algorithm.HMAC384(secret);
    }

    public static AlgorithmFactory hmac384(ResourceText secret) {
        return () -> Algorithm.HMAC384(secret.getTextAsOneLineAndTrim());
    }

    public static AlgorithmFactory hmac384WithRandomSecret() {
        return hmac384(UUID.randomUUID().toString());
    }

    public static AlgorithmFactory hmac512(String secret) {
        return () -> Algorithm.HMAC512(secret);
    }

    public static AlgorithmFactory hmac512(ResourceText secret) {
        return () -> Algorithm.HMAC512(secret.getTextAsOneLineAndTrim());
    }

    public static AlgorithmFactory hmac512WithRandomSecret() {
        return hmac512(UUID.randomUUID().toString());
    }

    // RSA
    // -----------------------------------------------------------------------------------------------------------------
    public static AlgorithmFactory rsa256(String publicKey, String privateKey) {
        return new RSA256AlgorithmFactory(publicKey, privateKey);
    }

    public static AlgorithmFactory rsa384(String publicKey, String privateKey) {
        return new RSA384AlgorithmFactory(publicKey, privateKey);
    }

    public static AlgorithmFactory rsa512(String publicKey, String privateKey) {
        return new RSA512AlgorithmFactory(publicKey, privateKey);
    }

    public static AlgorithmFactory rsa256(ResourceText publicKey, ResourceText privateKey) {
        return new RSA256AlgorithmFactory(publicKey, privateKey);
    }

    public static AlgorithmFactory rsa384(ResourceText publicKey, ResourceText privateKey) {
        return new RSA384AlgorithmFactory(publicKey, privateKey);
    }

    public static AlgorithmFactory rsa512(ResourceText publicKey, ResourceText privateKey) {
        return new RSA512AlgorithmFactory(publicKey, privateKey);
    }

    public static AlgorithmFactory predefinedRsa256(int number) {
        if (number < 0 || number > 7) {
            throw new IllegalArgumentException("number range: [0,7]");
        }
        return new RSA256AlgorithmFactory(
                ResourceText.of(String.format("classpath:com/github/yingzhuo/carnival/secret/predefined/rsa-%d-public", number)),
                ResourceText.of(String.format("classpath:com/github/yingzhuo/carnival/secret/predefined/rsa-%d-private", number))
        );
    }

    public static AlgorithmFactory predefinedRsa384(int number) {
        if (number < 0 || number > 7) {
            throw new IllegalArgumentException("number range: [0,7]");
        }
        return new RSA384AlgorithmFactory(
                ResourceText.of(String.format("classpath:com/github/yingzhuo/carnival/secret/predefined/rsa-%d-public", number)),
                ResourceText.of(String.format("classpath:com/github/yingzhuo/carnival/secret/predefined/rsa-%d-private", number))
        );
    }

    public static AlgorithmFactory predefinedRsa512(int number) {
        if (number < 0 || number > 7) {
            throw new IllegalArgumentException("number range: [0,7]");
        }
        return new RSA512AlgorithmFactory(
                ResourceText.of(String.format("classpath:com/github/yingzhuo/carnival/secret/predefined/rsa-%d-public", number)),
                ResourceText.of(String.format("classpath:com/github/yingzhuo/carnival/secret/predefined/rsa-%d-private", number))
        );
    }

    // ECDSA
    // -----------------------------------------------------------------------------------------------------------------
    public static AlgorithmFactory ecd256(String publicKey, String privateKey) {
        return new ECDSA256AlgorithmFactory(publicKey, privateKey);
    }

    public static AlgorithmFactory ecd256K(String publicKey, String privateKey) {
        return new ECDSA256KAlgorithmFactory(publicKey, privateKey);
    }

    public static AlgorithmFactory ecd384(String publicKey, String privateKey) {
        return new ECDSA384AlgorithmFactory(publicKey, privateKey);
    }

    public static AlgorithmFactory ecd512(String publicKey, String privateKey) {
        return new ECDSA512AlgorithmFactory(publicKey, privateKey);
    }

    public static AlgorithmFactory ecd256(ResourceText publicKey, ResourceText privateKey) {
        return new ECDSA256AlgorithmFactory(publicKey, privateKey);
    }

    public static AlgorithmFactory ecd256K(ResourceText publicKey, ResourceText privateKey) {
        return new ECDSA256KAlgorithmFactory(publicKey, privateKey);
    }

    public static AlgorithmFactory ecd384(ResourceText publicKey, ResourceText privateKey) {
        return new ECDSA384AlgorithmFactory(publicKey, privateKey);
    }

    public static AlgorithmFactory ecd512(ResourceText publicKey, ResourceText privateKey) {
        return new ECDSA512AlgorithmFactory(publicKey, privateKey);
    }

    public static AlgorithmFactory predefinedEcdsa256(int number) {
        if (number < 0 || number > 7) {
            throw new IllegalArgumentException("number range: [0,7]");
        }
        return new ECDSA256AlgorithmFactory(
                ResourceText.of(String.format("classpath:com/github/yingzhuo/carnival/secret/predefined/ecdsa-%d-public", number)),
                ResourceText.of(String.format("classpath:com/github/yingzhuo/carnival/secret/predefined/ecdsa-%d-private", number))
        );
    }

    public static AlgorithmFactory predefinedEcdsa256K(int number) {
        if (number < 0 || number > 7) {
            throw new IllegalArgumentException("number range: [0,7]");
        }
        return new ECDSA256KAlgorithmFactory(
                ResourceText.of(String.format("classpath:com/github/yingzhuo/carnival/secret/predefined/ecdsa-%d-public", number)),
                ResourceText.of(String.format("classpath:com/github/yingzhuo/carnival/secret/predefined/ecdsa-%d-private", number))
        );
    }

    public static AlgorithmFactory predefinedEcdsa384(int number) {
        if (number < 0 || number > 7) {
            throw new IllegalArgumentException("number range: [0,7]");
        }
        return new ECDSA384AlgorithmFactory(
                ResourceText.of(String.format("classpath:com/github/yingzhuo/carnival/secret/predefined/ecdsa-%d-public", number)),
                ResourceText.of(String.format("classpath:com/github/yingzhuo/carnival/secret/predefined/ecdsa-%d-private", number))
        );
    }

    public static AlgorithmFactory predefinedEcdsa512(int number) {
        if (number < 0 || number > 7) {
            throw new IllegalArgumentException("number range: [0,7]");
        }
        return new ECDSA512AlgorithmFactory(
                ResourceText.of(String.format("classpath:com/github/yingzhuo/carnival/secret/predefined/ecdsa-%d-public", number)),
                ResourceText.of(String.format("classpath:com/github/yingzhuo/carnival/secret/predefined/ecdsa-%d-private", number))
        );
    }

}
