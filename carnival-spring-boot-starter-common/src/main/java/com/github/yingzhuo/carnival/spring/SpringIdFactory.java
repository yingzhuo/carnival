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

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;

import java.util.UUID;

/**
 * @author 应卓
 * @see SpringUtils
 * @since 1.4.1
 */
public class SpringIdFactory implements EnvironmentPostProcessor {

    public static final String SPRING_ID = "spring.id";

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        environment.getPropertySources()
                .addFirst(new PropertySource<String>("springId", UUID.randomUUID().toString().replaceAll("-", "")) {
                    @Override
                    public Object getProperty(String name) {
                        if (SPRING_ID.equals(name)) {
                            return getSource();
                        }
                        return null;
                    }
                });
    }

}
