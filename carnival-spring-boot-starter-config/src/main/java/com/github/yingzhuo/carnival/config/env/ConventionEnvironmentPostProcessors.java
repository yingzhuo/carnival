/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.config.env;

import com.github.yingzhuo.carnival.config.util.JarDir;
import org.springframework.boot.SpringApplication;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @author 应卓
 * @since 1.6.15
 */
public interface ConventionEnvironmentPostProcessors {

    static class Property extends AbstractConventionEnvironmentPostProcessor {
        @Override
        protected String getName(ConfigurableEnvironment environment, SpringApplication application) {
            return "property";
        }

        @Override
        protected String[] getLocationsPrefix(ConfigurableEnvironment environment, SpringApplication application) {
            final Class<?> mainClass = application.getMainApplicationClass();
            return new String[]{
                    JarDir.of(mainClass).getDirAsResourceLocation("config/property"),
                    JarDir.of(mainClass).getDirAsResourceLocation(".config/property"),
                    JarDir.of(mainClass).getDirAsResourceLocation("_config/property"),
                    JarDir.of(mainClass).getDirAsResourceLocation("property"),
                    "file:config/property",
                    "file:.config/property",
                    "file:_config/property",
                    "file:property",
                    "classpath:config/property",
                    "classpath:.config/property",
                    "classpath:_config/property",
                    "classpath:property",
                    "classpath:META-INF/property"
            };
        }
    }

    static class ConfigMap extends AbstractConventionEnvironmentPostProcessor {
        @Override
        protected String getName(ConfigurableEnvironment environment, SpringApplication application) {
            return "config-map";
        }

        @Override
        protected String[] getLocationsPrefix(ConfigurableEnvironment environment, SpringApplication application) {
            final Class<?> mainClass = application.getMainApplicationClass();
            return new String[]{
                    JarDir.of(mainClass).getDirAsResourceLocation("config/config-map"),
                    JarDir.of(mainClass).getDirAsResourceLocation(".config/config-map"),
                    JarDir.of(mainClass).getDirAsResourceLocation("_config/config-map"),
                    JarDir.of(mainClass).getDirAsResourceLocation("config-map"),
                    "file:config/config-map",
                    "file:.config/config-map",
                    "file:_config/config-map",
                    "file:config-map",
                    "classpath:config/config-map",
                    "classpath:.config/config-map",
                    "classpath:_config/config-map",
                    "classpath:config-map",
                    "classpath:META-INF/config-map"
            };
        }
    }

    static class Secret extends AbstractConventionEnvironmentPostProcessor {
        @Override
        protected String getName(ConfigurableEnvironment environment, SpringApplication application) {
            return "secret";
        }

        @Override
        protected String[] getLocationsPrefix(ConfigurableEnvironment environment, SpringApplication application) {
            final Class<?> mainClass = application.getMainApplicationClass();
            return new String[]{
                    JarDir.of(mainClass).getDirAsResourceLocation("config/secret"),
                    JarDir.of(mainClass).getDirAsResourceLocation(".config/secret"),
                    JarDir.of(mainClass).getDirAsResourceLocation("_config/secret"),
                    JarDir.of(mainClass).getDirAsResourceLocation("secret"),
                    "file:config/secret",
                    "file:.config/secret",
                    "file:_config/secret",
                    "file:secret",
                    "classpath:config/secret",
                    "classpath:.config/secret",
                    "classpath:_config/secret",
                    "classpath:secret",
                    "classpath:META-INF/secret"
            };
        }
    }

    static class Git extends AbstractConventionEnvironmentPostProcessor {
        @Override
        protected String getName(ConfigurableEnvironment environment, SpringApplication application) {
            return "git";
        }

        @Override
        protected String[] getLocationsPrefix(ConfigurableEnvironment environment, SpringApplication application) {
            return new String[]{"classpath:git", "classpath:META-INF/git", "classpath:config/git"};
        }
    }

}
