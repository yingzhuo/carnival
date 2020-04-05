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
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;

import java.util.UUID;

/**
 * @author 应卓
 * @since 1.4.11
 */
public class ExtEnvironmentPostProcessor implements EnvironmentPostProcessor {

    public static final String SPRING_ID = "spring.id";

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        environment.getPropertySources()
                .addFirst(new ExtPropertySource());
    }

    private static class ExtPropertySource extends PropertySource<Object> {

        private static final String PREFIX = "random.";

        public ExtPropertySource() {
            super("carnival-ext", new Object());
        }

        @Override
        public Object getProperty(String name) {

            // ${spring.id}
            if (SPRING_ID.equals(name)) {
                return UUID.randomUUID().toString().replaceAll("-", "");
            }

            try {
                return doGetProperty(name);
            } catch (NumberFormatException e) {
                return null;
            }
        }

        private Object doGetProperty(String name) {
            if (!name.startsWith(PREFIX)) {
                return null;
            }
            name = name.substring(PREFIX.length()).trim();

            if (name.startsWith("alphabetic(") && name.endsWith(")")) {
                name = name.substring("alphabetic(".length(), name.length() - 1);
                return RandomStringUtils.randomAlphabetic(Integer.parseInt(name));
            }

            if (name.startsWith("alphanumeric(") && name.endsWith(")")) {
                name = name.substring("alphanumeric(".length(), name.length() - 1);
                return RandomStringUtils.randomAlphanumeric(Integer.parseInt(name));
            }

            if (name.startsWith("numeric(") && name.endsWith(")")) {
                name = name.substring("numeric(".length(), name.length() - 1);
                return RandomStringUtils.randomNumeric(Integer.parseInt(name));
            }
            return null;
        }
    }

}
