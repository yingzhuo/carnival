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

import java.util.Objects;

/**
 * @author 应卓
 * @since 1.6.0
 */
public class HMAC384AlgorithmFactory implements AlgorithmFactory {

    private final String secret;

    public HMAC384AlgorithmFactory() {
        this(Algorithm.class.getName());
    }

    public HMAC384AlgorithmFactory(String secret) {
        this.secret = Objects.requireNonNull(secret);
    }

    @Override
    public Algorithm create() {
        return Algorithm.HMAC384(secret);
    }

}
