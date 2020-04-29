/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.flow.signature;

import com.auth0.jwt.algorithms.Algorithm;
import com.github.yingzhuo.carnival.common.io.ResourceText;

/**
 * @author 应卓
 * @since 1.6.1
 */
public final class AlgorithmGenerators {

    // None
    // -----------------------------------------------------------------------------------------------------------------
    public static AlgorithmGenerator none() {
        return Algorithm::none;
    }

    // HMAC
    // -----------------------------------------------------------------------------------------------------------------
    public static AlgorithmGenerator hmac256(String secret) {
        return () -> Algorithm.HMAC256(secret);
    }

    public static AlgorithmGenerator hmac256(ResourceText secret) {
        return () -> Algorithm.HMAC256(secret.getTextAsOneLine().trim());
    }

    public static AlgorithmGenerator hmac384(String secret) {
        return () -> Algorithm.HMAC384(secret);
    }

    public static AlgorithmGenerator hmac384(ResourceText secret) {
        return () -> Algorithm.HMAC384(secret.getTextAsOneLine().trim());
    }

    public static AlgorithmGenerator hmac512(String secret) {
        return () -> Algorithm.HMAC512(secret);
    }

    public static AlgorithmGenerator hmac512(ResourceText secret) {
        return () -> Algorithm.HMAC512(secret.getTextAsOneLine().trim());
    }

    // RSA
    // -----------------------------------------------------------------------------------------------------------------
    public static AlgorithmGenerator rsa256(String publicKey, String privateKey) {
        return new RSA256AlgorithmGenerator(publicKey, privateKey);
    }

    public static AlgorithmGenerator rsa384(String publicKey, String privateKey) {
        return new RSA384AlgorithmGenerator(publicKey, privateKey);
    }

    public static AlgorithmGenerator rsa512(String publicKey, String privateKey) {
        return new RSA512AlgorithmGenerator(publicKey, privateKey);
    }

    public static AlgorithmGenerator rsa256(ResourceText publicKey, ResourceText privateKey) {
        return new RSA256AlgorithmGenerator(publicKey, privateKey);
    }

    public static AlgorithmGenerator rsa384(ResourceText publicKey, ResourceText privateKey) {
        return new RSA384AlgorithmGenerator(publicKey, privateKey);
    }

    public static AlgorithmGenerator rsa512(ResourceText publicKey, ResourceText privateKey) {
        return new RSA512AlgorithmGenerator(publicKey, privateKey);
    }

    public static AlgorithmGenerator preinstalledRsa256(int number) {
        if (number < 0 || number > 9) {
            throw new IllegalArgumentException("number range: [0,10)");
        }
        return new RSA256AlgorithmGenerator(
                ResourceText.of(String.format("classpath:/META-INF/carnival-spring-boot-starter-restful-flow/rsa/pair%d/public", number)),
                ResourceText.of(String.format("classpath:/META-INF/carnival-spring-boot-starter-restful-flow/rsa/pair%d/private", number))
        );
    }

    public static AlgorithmGenerator preinstalledRsa384(int number) {
        if (number < 0 || number > 9) {
            throw new IllegalArgumentException("number range: [0,10)");
        }
        return new RSA384AlgorithmGenerator(
                ResourceText.of(String.format("classpath:/META-INF/carnival-spring-boot-starter-restful-flow/rsa/pair%d/public", number)),
                ResourceText.of(String.format("classpath:/META-INF/carnival-spring-boot-starter-restful-flow/rsa/pair%d/private", number))
        );
    }

    public static AlgorithmGenerator preinstalledRsa512(int number) {
        if (number < 0 || number > 9) {
            throw new IllegalArgumentException("number range: [0,10)");
        }
        return new RSA512AlgorithmGenerator(
                ResourceText.of(String.format("classpath:/META-INF/carnival-spring-boot-starter-restful-flow/rsa/pair%d/public", number)),
                ResourceText.of(String.format("classpath:/META-INF/carnival-spring-boot-starter-restful-flow/rsa/pair%d/private", number))
        );
    }

    // ECDSA
    // -----------------------------------------------------------------------------------------------------------------
    public static AlgorithmGenerator ecd256(String publicKey, String privateKey) {
        return new ECDSA256AlgorithmGenerator(publicKey, privateKey);
    }

    public static AlgorithmGenerator ecd384(String publicKey, String privateKey) {
        return new ECDSA384AlgorithmGenerator(publicKey, privateKey);
    }

    public static AlgorithmGenerator ecd512(String publicKey, String privateKey) {
        return new ECDSA512AlgorithmGenerator(publicKey, privateKey);
    }

    public static AlgorithmGenerator ecd256(ResourceText publicKey, ResourceText privateKey) {
        return new ECDSA256AlgorithmGenerator(publicKey, privateKey);
    }

    public static AlgorithmGenerator ecd384(ResourceText publicKey, ResourceText privateKey) {
        return new ECDSA384AlgorithmGenerator(publicKey, privateKey);
    }

    public static AlgorithmGenerator ecd512(ResourceText publicKey, ResourceText privateKey) {
        return new ECDSA512AlgorithmGenerator(publicKey, privateKey);
    }

    public static AlgorithmGenerator preinstalledEcdsa256(int number) {
        if (number < 0 || number > 9) {
            throw new IllegalArgumentException("number range: [0,10)");
        }
        return new ECDSA256AlgorithmGenerator(
                ResourceText.of(String.format("classpath:/META-INF/carnival-spring-boot-starter-restful-flow/ecdsa/pair%d/public", number)),
                ResourceText.of(String.format("classpath:/META-INF/carnival-spring-boot-starter-restful-flow/ecdsa/pair%d/private", number))
        );
    }

    public static AlgorithmGenerator preinstalledEcdsa384(int number) {
        if (number < 0 || number > 9) {
            throw new IllegalArgumentException("number range: [0,10)");
        }
        return new ECDSA384AlgorithmGenerator(
                ResourceText.of(String.format("classpath:/META-INF/carnival-spring-boot-starter-restful-flow/ecdsa/pair%d/public", number)),
                ResourceText.of(String.format("classpath:/META-INF/carnival-spring-boot-starter-restful-flow/ecdsa/pair%d/private", number))
        );
    }

    public static AlgorithmGenerator preinstalledEcdsa512(int number) {
        if (number < 0 || number > 9) {
            throw new IllegalArgumentException("number range: [0,10)");
        }
        return new ECDSA512AlgorithmGenerator(
                ResourceText.of(String.format("classpath:/META-INF/carnival-spring-boot-starter-restful-flow/ecdsa/pair%d/public", number)),
                ResourceText.of(String.format("classpath:/META-INF/carnival-spring-boot-starter-restful-flow/ecdsa/pair%d/private", number))
        );
    }

    // -----------------------------------------------------------------------------------------------------------------
    private AlgorithmGenerators() {
    }

}
