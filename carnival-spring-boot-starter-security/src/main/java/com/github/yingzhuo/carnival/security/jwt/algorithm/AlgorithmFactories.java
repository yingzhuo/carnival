/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.security.jwt.algorithm;

import com.auth0.jwt.algorithms.Algorithm;

/**
 * @author 应卓
 * @since 1.10.2
 */
public final class AlgorithmFactories {

    private AlgorithmFactories() {
    }

    public static AlgorithmFactory none() {
        return Algorithm::none;
    }

    public static AlgorithmFactory hmac256(final String secret) {
        return () -> Algorithm.HMAC256(secret);
    }

    public static AlgorithmFactory hmac384(final String secret) {
        return () -> Algorithm.HMAC384(secret);
    }

    public static AlgorithmFactory hmac512(final String secret) {
        return () -> Algorithm.HMAC512(secret);
    }

    public static AlgorithmFactory rsa256(final String publicKey, final String privateKey) {
        return new RSA256AlgorithmFactory(publicKey, privateKey);
    }

    public static AlgorithmFactory rsa384(final String publicKey, final String privateKey) {
        return new RSA384AlgorithmFactory(publicKey, privateKey);
    }

    public static AlgorithmFactory rsa512(final String publicKey, final String privateKey) {
        return new RSA512AlgorithmFactory(publicKey, privateKey);
    }

    public static AlgorithmFactory ecdsa256(final String publicKey, final String privateKey) {
        return new ECDSA256AlgorithmFactory(publicKey, privateKey);
    }

    public static AlgorithmFactory ecdsa256k(final String publicKey, final String privateKey) {
        return new ECDSA256KAlgorithmFactory(publicKey, privateKey);
    }

    public static AlgorithmFactory ecdsa384(final String publicKey, final String privateKey) {
        return new ECDSA384AlgorithmFactory(publicKey, privateKey);
    }

    public static AlgorithmFactory ecdsa512(final String publicKey, final String privateKey) {
        return new ECDSA512AlgorithmFactory(publicKey, privateKey);
    }

    public static AlgorithmFactory fromPemFile(PemFileAlgorithmFactory.Alg alg, final String publicKeyLocation, final String privateKeyLocation) {
        return new PemFileAlgorithmFactory(alg, publicKeyLocation, privateKeyLocation);
    }

    public static AlgorithmFactory random() {
        return new RandomAlgorithmFactory();
    }

}
