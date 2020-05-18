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

import com.github.yingzhuo.springboot.env.support.AbstractConventionEnvironmentPostProcessor;
import com.github.yingzhuo.springboot.env.util.JarDir;
import org.springframework.boot.SpringApplication;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @author 应卓
 * @since 1.6.4
 */
public interface ConventionEnvironmentPostProcessors {

    static class PropertySource extends AbstractConventionEnvironmentPostProcessor {
        @Override
        protected String getName(ConfigurableEnvironment environment, SpringApplication application) {
            return "property-source";
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

    static class BusinessException extends AbstractConventionEnvironmentPostProcessor {
        @Override
        protected String getName(ConfigurableEnvironment environment, SpringApplication application) {
            return "business-exception";
        }

        @Override
        protected String[] getLocationsPrefix(ConfigurableEnvironment environment, SpringApplication application) {
            final Class<?> mainClass = application.getMainApplicationClass();
            return new String[]{
                    JarDir.of(mainClass).getDirAsResourceLocation("config/business-exception"),
                    JarDir.of(mainClass).getDirAsResourceLocation(".config/business-exception"),
                    JarDir.of(mainClass).getDirAsResourceLocation("_config/business-exception"),
                    JarDir.of(mainClass).getDirAsResourceLocation("business-exception"),
                    "file:config/business-exception",
                    "file:.config/business-exception",
                    "file:_config/business-exception",
                    "file:business-exception",
                    "classpath:config/business-exception",
                    "classpath:.config/business-exception",
                    "classpath:_config/business-exception",
                    "classpath:business-exception",
                    "classpath:META-INF/business-exception"
            };
        }
    }

    static class DataSource extends AbstractConventionEnvironmentPostProcessor {
        @Override
        protected String getName(ConfigurableEnvironment environment, SpringApplication application) {
            return "datasource";
        }

        @Override
        protected String[] getLocationsPrefix(ConfigurableEnvironment environment, SpringApplication application) {
            final Class<?> mainClass = application.getMainApplicationClass();
            return new String[]{
                    JarDir.of(mainClass).getDirAsResourceLocation("config/datasource"),
                    JarDir.of(mainClass).getDirAsResourceLocation(".config/datasource"),
                    JarDir.of(mainClass).getDirAsResourceLocation("_config/datasource"),
                    JarDir.of(mainClass).getDirAsResourceLocation("datasource"),
                    "file:config/datasource",
                    "file:.config/datasource",
                    "file:_config/datasource",
                    "file:datasource",
                    "classpath:config/datasource",
                    "classpath:.config/datasource",
                    "classpath:_config/datasource",
                    "classpath:datasource",
                    "classpath:META-INF/datasource"
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
            return new String[]{"classpath:git", "classpath:META-INF/git"};
        }
    }

    static class BuildInfo extends AbstractConventionEnvironmentPostProcessor {

        @Override
        protected String getName(ConfigurableEnvironment environment, SpringApplication application) {
            return "build-info";
        }

        @Override
        protected String[] getLocationsPrefix(ConfigurableEnvironment environment, SpringApplication application) {
            return new String[]{"classpath:build-info", "classpath:META-INF/build-info"};
        }
    }

}
