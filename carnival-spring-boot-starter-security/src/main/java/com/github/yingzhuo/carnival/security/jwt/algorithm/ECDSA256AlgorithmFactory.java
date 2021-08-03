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

import java.util.Objects;

/**
 * @author 应卓
 * @since 1.10.2
 */
final class ECDSA256AlgorithmFactory extends ECDSAAlgorithmFactory {

    private final String publicKey;
    private final String privateKey;

    public ECDSA256AlgorithmFactory(String publicKey, String privateKey) {
        this.publicKey = Objects.requireNonNull(publicKey);
        this.privateKey = Objects.requireNonNull(privateKey);
    }

    @Override
    public Algorithm getObject() {
        return Algorithm.ECDSA256(toPublicKey(publicKey), toPrivateKey(privateKey));
    }
}
