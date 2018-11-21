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
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;

/**
 * @author 应卓
 */
public class PropertiesSecretLoader implements SecretLoader, InitializingBean {

    private final ResourceLoader resourceLoader = new DefaultResourceLoader();
    private final Properties properties = new Properties();
    private final Resource resource;

    public PropertiesSecretLoader(Resource resource) {
        this.resource = Objects.requireNonNull(resource);
    }

    @Override
    public Optional<String> load(String clientId) {
        return Optional.ofNullable(properties.getProperty(clientId, null));
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        try {
            properties.load(resource.getInputStream());
        } finally {

            try {
                resource.getInputStream().close();
            } catch (IOException e) {
                // NOP
            }
        }
    }

}
