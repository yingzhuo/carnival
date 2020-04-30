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

import com.auth0.jwt.algorithms.Algorithm;
import com.github.yingzhuo.carnival.common.io.ResourceText;

/**
 * @author 应卓
 * @since 1.6.1
 */
public final class AlgorithmFactories {

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

    public static AlgorithmFactory hmac384(String secret) {
        return () -> Algorithm.HMAC384(secret);
    }

    public static AlgorithmFactory hmac384(ResourceText secret) {
        return () -> Algorithm.HMAC384(secret.getTextAsOneLineAndTrim());
    }

    public static AlgorithmFactory hmac512(String secret) {
        return () -> Algorithm.HMAC512(secret);
    }

    public static AlgorithmFactory hmac512(ResourceText secret) {
        return () -> Algorithm.HMAC512(secret.getTextAsOneLineAndTrim());
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

    public static AlgorithmFactory preinstalledRsa256(int number) {
        if (number < 0 || number > 15) {
            throw new IllegalArgumentException("number range: [0, 16]");
        }
        return new RSA256AlgorithmFactory(
                ResourceText.of(String.format("classpath:/META-INF/carnival-spring-boot-starter-restful-security-jwt/rsa/pair%d/public", number)),
                ResourceText.of(String.format("classpath:/META-INF/carnival-spring-boot-starter-restful-security-jwt/rsa/pair%d/private", number))
        );
    }

    public static AlgorithmFactory preinstalledRsa384(int number) {
        if (number < 0 || number > 15) {
            throw new IllegalArgumentException("number range: [0, 16]");
        }
        return new RSA384AlgorithmFactory(
                ResourceText.of(String.format("classpath:/META-INF/carnival-spring-boot-starter-restful-security-jwt/rsa/pair%d/public", number)),
                ResourceText.of(String.format("classpath:/META-INF/carnival-spring-boot-starter-restful-security-jwt/rsa/pair%d/private", number))
        );
    }

    public static AlgorithmFactory preinstalledRsa512(int number) {
        if (number < 0 || number > 15) {
            throw new IllegalArgumentException("number range: [0, 16]");
        }
        return new RSA512AlgorithmFactory(
                ResourceText.of(String.format("classpath:/META-INF/carnival-spring-boot-starter-restful-security-jwt/rsa/pair%d/public", number)),
                ResourceText.of(String.format("classpath:/META-INF/carnival-spring-boot-starter-restful-security-jwt/rsa/pair%d/private", number))
        );
    }

    // ECDSA
    // -----------------------------------------------------------------------------------------------------------------
    public static AlgorithmFactory ecd256(String publicKey, String privateKey) {
        return new ECDSA256AlgorithmFactory(publicKey, privateKey);
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

    public static AlgorithmFactory ecd384(ResourceText publicKey, ResourceText privateKey) {
        return new ECDSA384AlgorithmFactory(publicKey, privateKey);
    }

    public static AlgorithmFactory ecd512(ResourceText publicKey, ResourceText privateKey) {
        return new ECDSA512AlgorithmFactory(publicKey, privateKey);
    }

    public static AlgorithmFactory preinstalledEcdsa256(int number) {
        if (number < 0 || number > 15) {
            throw new IllegalArgumentException("number range: [0, 16]");
        }
        return new ECDSA256AlgorithmFactory(
                ResourceText.of(String.format("classpath:/META-INF/carnival-spring-boot-starter-restful-security-jwt/ecdsa/pair%d/public", number)),
                ResourceText.of(String.format("classpath:/META-INF/carnival-spring-boot-starter-restful-security-jwt/ecdsa/pair%d/private", number))
        );
    }

    public static AlgorithmFactory preinstalledEcdsa384(int number) {
        if (number < 0 || number > 15) {
            throw new IllegalArgumentException("number range: [0, 16]");
        }
        return new ECDSA384AlgorithmFactory(
                ResourceText.of(String.format("classpath:/META-INF/carnival-spring-boot-starter-restful-security-jwt/ecdsa/pair%d/public", number)),
                ResourceText.of(String.format("classpath:/META-INF/carnival-spring-boot-starter-restful-security-jwt/ecdsa/pair%d/private", number))
        );
    }

    public static AlgorithmFactory preinstalledEcdsa512(int number) {
        if (number < 0 || number > 15) {
            throw new IllegalArgumentException("number range: [0, 16]");
        }
        return new ECDSA512AlgorithmFactory(
                ResourceText.of(String.format("classpath:/META-INF/carnival-spring-boot-starter-restful-security-jwt/ecdsa/pair%d/public", number)),
                ResourceText.of(String.format("classpath:/META-INF/carnival-spring-boot-starter-restful-security-jwt/ecdsa/pair%d/private", number))
        );
    }

    // -----------------------------------------------------------------------------------------------------------------
    private AlgorithmFactories() {
    }

}
