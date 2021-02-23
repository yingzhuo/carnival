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

import java.util.Objects;

/**
 * @author 应卓
 * @since 1.6.0
 */
class ECDSA384AlgorithmFactory extends AbstractECDSAAlgorithmFactory {

    private final String publicKey;
    private final String privateKey;

    public ECDSA384AlgorithmFactory(String publicKey, String privateKey) {
        this.publicKey = Objects.requireNonNull(publicKey);
        this.privateKey = Objects.requireNonNull(privateKey);
    }

    public ECDSA384AlgorithmFactory(ResourceText publicKey, ResourceText privateKey) {
        this.publicKey = Objects.requireNonNull(publicKey).getTextAsOneLineAndTrim();
        this.privateKey = Objects.requireNonNull(privateKey).getTextAsOneLineAndTrim();
    }

    @Override
    public Algorithm create() {
        return Algorithm.ECDSA384(toPublicKey(publicKey), toPrivateKey(privateKey));
    }

}
