/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.websecret.dao;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author 应卓
 */
public class PropertiesSecretLoader implements SecretLoader, InitializingBean {

    public static final String DEFAULT_RESOURCE_LOADER = "classpath:/websecret.properties";

    private final ResourceLoader resourceLoader = new DefaultResourceLoader();
    private final Properties properties = new Properties();

    @Override
    public String load(String clientId) {
        return properties.getProperty(clientId);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        InputStream ins = resourceLoader.getResource(DEFAULT_RESOURCE_LOADER).getInputStream();
        properties.load(ins);

        try {
            ins.close();
        } catch (IOException e) {
            /* NOP */
        }
    }

}
