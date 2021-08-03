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
import org.springframework.beans.factory.SmartFactoryBean;

/**
 * @author 应卓
 * @since 1.10.2
 */
@FunctionalInterface
public interface AlgorithmFactory extends SmartFactoryBean<Algorithm> {

    @Override
    public default Class<?> getObjectType() {
        return Algorithm.class;
    }

    @Override
    public default boolean isSingleton() {
        return true;
    }

    @Override
    public default boolean isPrototype() {
        return false;
    }

    @Override
    public default boolean isEagerInit() {
        return true;
    }

}
