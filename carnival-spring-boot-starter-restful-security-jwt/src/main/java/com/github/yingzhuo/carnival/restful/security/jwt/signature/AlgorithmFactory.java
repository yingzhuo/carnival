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

import java.util.function.Supplier;

/**
 * @author 应卓
 * @since 1.6.0
 */
@FunctionalInterface
public interface AlgorithmFactory extends Supplier<Algorithm> {

    public Algorithm create();

    @Override
    public default Algorithm get() {
        return create();
    }

}
