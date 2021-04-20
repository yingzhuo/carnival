/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.qrcode;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.core.io.Resource;

/**
 * @author 应卓
 * @since 1.8.5
 */
public class LogoFactoryBean implements FactoryBean<Logo> {

    private Resource location;

    @Override
    public Logo getObject() {
        return Logo.builder()
                .image(location)
                .build();
    }

    @Override
    public Class<?> getObjectType() {
        return Logo.class;
    }

    public void setLocation(Resource location) {
        this.location = location;
    }

}
