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
import com.github.yingzhuo.carnival.restful.security.jwt.signature.support.AbstractRSAAlgorithmFactory;

import java.util.Objects;

/**
 * @author 应卓
 * @since 1.6.0
 */
public class RSA384AlgorithmFactory extends AbstractRSAAlgorithmFactory {

    private final String publicKey;
    private final String privateKey;

    public RSA384AlgorithmFactory(String publicKey, String privateKey) {
        this.publicKey = Objects.requireNonNull(publicKey);
        this.privateKey = Objects.requireNonNull(privateKey);
    }

    public RSA384AlgorithmFactory(ResourceText publicKey, ResourceText privateKey) {
        this.publicKey = Objects.requireNonNull(publicKey).getTextAsOneLine().trim();
        this.privateKey = Objects.requireNonNull(privateKey).getTextAsOneLine().trim();
    }

    @Override
    public Algorithm create() {
        return Algorithm.RSA384(toPublicKey(publicKey), toPrivateKey(privateKey));
    }

}
