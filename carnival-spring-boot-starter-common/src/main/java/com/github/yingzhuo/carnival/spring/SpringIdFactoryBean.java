/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.spring;

import org.springframework.beans.factory.FactoryBean;

/**
 * @author 应卓
 * @since 1.3.6
 */
@FunctionalInterface
public interface SpringIdFactoryBean extends FactoryBean<String> {

    @Override
    default Class<?> getObjectType() {
        return String.class;
    }

    @Override
    default boolean isSingleton() {
        return true;
    }

}
