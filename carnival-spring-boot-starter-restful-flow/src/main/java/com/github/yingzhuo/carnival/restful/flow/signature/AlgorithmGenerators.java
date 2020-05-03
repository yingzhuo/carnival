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
        return () -> Algorithm.HMAC256(secret.getTextAsOneLineAndTrim());
    }

    public static AlgorithmGenerator hmac384(String secret) {
        return () -> Algorithm.HMAC384(secret);
    }

    public static AlgorithmGenerator hmac384(ResourceText secret) {
        return () -> Algorithm.HMAC384(secret.getTextAsOneLineAndTrim());
    }

    public static AlgorithmGenerator hmac512(String secret) {
        return () -> Algorithm.HMAC512(secret);
    }

    public static AlgorithmGenerator hmac512(ResourceText secret) {
        return () -> Algorithm.HMAC512(secret.getTextAsOneLineAndTrim());
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

    public static AlgorithmGenerator predefinedRsa256(int number) {
        if (number < 0 || number > 15) {
            throw new IllegalArgumentException("number range: [0,16)");
        }
        return new RSA256AlgorithmGenerator(
                ResourceText.of(String.format("classpath:/com.github.yingzhuo.carnival/RSA/pair%d/public", number)),
                ResourceText.of(String.format("classpath:/com.github.yingzhuo.carnival/RSA/pair%d/private", number))
        );
    }

    public static AlgorithmGenerator predefinedRsa384(int number) {
        if (number < 0 || number > 15) {
            throw new IllegalArgumentException("number range: [0,16)");
        }
        return new RSA384AlgorithmGenerator(
                ResourceText.of(String.format("classpath:/com.github.yingzhuo.carnival/RSA/pair%d/public", number)),
                ResourceText.of(String.format("classpath:/com.github.yingzhuo.carnival/RSA/pair%d/private", number))
        );
    }

    public static AlgorithmGenerator predefinedRsa512(int number) {
        if (number < 0 || number > 15) {
            throw new IllegalArgumentException("number range: [0,16)");
        }
        return new RSA512AlgorithmGenerator(
                ResourceText.of(String.format("classpath:/com.github.yingzhuo.carnival/RSA/pair%d/public", number)),
                ResourceText.of(String.format("classpath:/com.github.yingzhuo.carnival/RSA/pair%d/private", number))
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

    public static AlgorithmGenerator predefinedEcdsa256(int number) {
        if (number < 0 || number > 15) {
            throw new IllegalArgumentException("number range: [0,16)");
        }
        return new ECDSA256AlgorithmGenerator(
                ResourceText.of(String.format("classpath:/com.github.yingzhuo.carnival/ECDSA/pair%d/public", number)),
                ResourceText.of(String.format("classpath:/com.github.yingzhuo.carnival/ECDSA/pair%d/private", number))
        );
    }

    public static AlgorithmGenerator predefinedEcdsa384(int number) {
        if (number < 0 || number > 15) {
            throw new IllegalArgumentException("number range: [0,16)");
        }
        return new ECDSA384AlgorithmGenerator(
                ResourceText.of(String.format("classpath:/com.github.yingzhuo.carnival/ECDSA/pair%d/public", number)),
                ResourceText.of(String.format("classpath:/com.github.yingzhuo.carnival/ECDSA/pair%d/private", number))
        );
    }

    public static AlgorithmGenerator predefinedEcdsa512(int number) {
        if (number < 0 || number > 15) {
            throw new IllegalArgumentException("number range: [0,16)");
        }
        return new ECDSA512AlgorithmGenerator(
                ResourceText.of(String.format("classpath:/com.github.yingzhuo.carnival/ECDSA/pair%d/public", number)),
                ResourceText.of(String.format("classpath:/com.github.yingzhuo.carnival/ECDSA/pair%d/private", number))
        );
    }

    // -----------------------------------------------------------------------------------------------------------------
    private AlgorithmGenerators() {
    }

}
