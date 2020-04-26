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

/**
 * @author 应卓
 * @since 1.4.11
 */
public class EnhancedRandomValueEnvironmentPostProcessor implements EnvironmentPostProcessor {

    private static final String PREFIX = "random.";

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        environment.getPropertySources().addFirst(new PropertySource<Object>("enhancedRandom") {

            @Override
            public Object getProperty(String name) {
                try {
                    return doGetProperty(name);
                } catch (Exception e) {
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
                    return RandomStringUtils.randomAlphabetic(Integer.parseInt(name.trim()));
                }

                if (name.startsWith("alphanumeric(") && name.endsWith(")")) {
                    name = name.substring("alphanumeric(".length(), name.length() - 1);
                    return RandomStringUtils.randomAlphanumeric(Integer.parseInt(name.trim()));
                }

                if (name.startsWith("numeric(") && name.endsWith(")")) {
                    name = name.substring("numeric(".length(), name.length() - 1);
                    return RandomStringUtils.randomNumeric(Integer.parseInt(name.trim()));
                }

                if (name.startsWith("ascii(") && name.endsWith(")")) {
                    name = name.substring("ascii(".length(), name.length() - 1);
                    return RandomStringUtils.randomAscii(Integer.parseInt(name.trim()));
                }

                if (name.startsWith("print(") && name.endsWith(")")) {
                    name = name.substring("print(".length(), name.length() - 1);
                    return RandomStringUtils.randomPrint(Integer.parseInt(name.trim()));
                }

                return null;
            }
        });
    }

}
