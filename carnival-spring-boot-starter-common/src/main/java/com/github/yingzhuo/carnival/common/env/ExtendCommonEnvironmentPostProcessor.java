/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.common.env;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;

/**
 * @author 应卓
 * @since 1.4.0
 */
public class ExtendCommonEnvironmentPostProcessor implements EnvironmentPostProcessor, Ordered {

    private static final String PROPERTY_SOURCE_NAME = "randomInstanceId";

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        environment.getPropertySources()
                .addFirst(new RandomInstanceIdPropertySource(PROPERTY_SOURCE_NAME));
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

    private static class RandomInstanceIdPropertySource extends PropertySource<Object> {
        public RandomInstanceIdPropertySource(String name) {
            super(name);
        }

        @Override
        public boolean containsProperty(String name) {
            return "random.instance-id".equals(name);
        }

        @Override
        public Object getProperty(String name) {
            if ("random.instance-id".equals(name)) {
                String id = RandomStringUtils.randomAlphabetic(3) + RandomStringUtils.randomNumeric(10);
                return id.toUpperCase();
            }
            return null;
        }
    }

}
