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

import java.util.UUID;

/**
 * @author 应卓
 */
public final class SpringIdFactoryBean implements FactoryBean<String> {

    @Override
    public String getObject() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    @Override
    public Class<?> getObjectType() {
        return String.class;
    }

}
