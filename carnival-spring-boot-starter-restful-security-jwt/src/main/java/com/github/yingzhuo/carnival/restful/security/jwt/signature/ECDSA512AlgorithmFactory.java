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

import java.util.Objects;

/**
 * @author 应卓
 * @since 1.6.0
 */
public class ECDSA512AlgorithmFactory extends AbstractECDSAAlgorithmFactory {

    private final String publicKey;
    private final String privateKey;

    public ECDSA512AlgorithmFactory(ResourceText publicKey, ResourceText privateKey) {
        this.publicKey = Objects.requireNonNull(publicKey).getTextAsOneLine();
        this.privateKey = Objects.requireNonNull(privateKey).getTextAsOneLine();
    }

    @Override
    public Algorithm create() {
        return Algorithm.ECDSA512(toPublicKey(publicKey), toPrivateKey(privateKey));
    }

}
