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

import com.github.yingzhuo.carnival.common.io.ResourceOption;
import lombok.val;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.util.Properties;

/**
 * @author 应卓
 */
public class PropertiesSecretLoader implements SecretLoader, InitializingBean {

    private final ResourceLoader resourceLoader = new DefaultResourceLoader();
    private final Properties properties = new Properties();

    @Override
    public String load(String clientId) {
        return properties.getProperty(clientId);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        val resourceOp = ResourceOption.of(
                "file:./websecret.properties",
                "classpath:/websecret.properties",
                "classpath:/META-INF/websecret.properties");

        if (resourceOp.isPresent()) {

            try {
                properties.load(resourceOp.get().getInputStream());
            } finally {

                try {
                    resourceOp.get().getInputStream().close();
                } catch (IOException e) {
                    // NOP
                }
            }
        }
    }

}
