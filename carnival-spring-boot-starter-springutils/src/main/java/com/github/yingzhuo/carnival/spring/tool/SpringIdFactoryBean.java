/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.spring.tool;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import java.util.UUID;

public final class SpringIdFactoryBean implements InitializingBean, FactoryBean<String> {

    private String uuid;

    @Override
    public void afterPropertiesSet() throws Exception {
        this.uuid = UUID.randomUUID().toString();
    }

    @Override
    public String getObject() throws Exception {
        return this.uuid;
    }

    @Override
    public Class<?> getObjectType() {
        return String.class;
    }

}
