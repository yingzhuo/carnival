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

/**
 * @author 应卓
 * @since 1.6.1
 */
public final class AlgorithmGenerators {

    public static AlgorithmGenerator none() {
        return Algorithm::none;
    }

    public static AlgorithmGenerator hmac256(String secret) {
        return () -> Algorithm.HMAC256(secret);
    }

    public static AlgorithmGenerator hmac384(String secret) {
        return () -> Algorithm.HMAC384(secret);
    }

    public static AlgorithmGenerator hmac512(String secret) {
        return () -> Algorithm.HMAC512(secret);
    }

    // -----------------------------------------------------------------------------------------------------------------

    private AlgorithmGenerators() {
    }

}
