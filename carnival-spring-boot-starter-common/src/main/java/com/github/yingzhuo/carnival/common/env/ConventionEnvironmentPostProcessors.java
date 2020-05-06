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
public final class ConventionEnvironmentPostProcessors {

    private static String getMainClassPrefix(SpringApplication application, String prefix) {
        final Class<?> mainClass = application.getMainApplicationClass();
        final String packageName = mainClass.getPackage().getName().replaceAll("\\.", "/");
        return "classpath:" + packageName + "/" + prefix;
    }

    public static class BusinessException extends AbstractConventionEnvironmentPostProcessor {
        @Override
        protected String overwriteName(ConfigurableEnvironment environment, SpringApplication application) {
            return "business-exception";
        }

        @Override
        protected String[] overwriteLocationsPrefix(ConfigurableEnvironment environment, SpringApplication application) {
            return new String[]{
                    JarDir.of().getDirAsResourceLocation("config/business-exception"),
                    JarDir.of().getDirAsResourceLocation(".config/business-exception"),
                    JarDir.of().getDirAsResourceLocation("_config/business-exception"),
                    JarDir.of().getDirAsResourceLocation("business-exception"),
                    "file:config/business-exception",
                    "file:.config/business-exception",
                    "file:_config/business-exception",
                    "file:business-exception",
                    "classpath:config/business-exception",
                    "classpath:.config/business-exception",
                    "classpath:_config/business-exception",
                    "classpath:business-exception",
                    "classpath:META-INF/business-exception",
                    getMainClassPrefix(application, "business-exception")
            };
        }
    }

    public static class DataSource extends AbstractConventionEnvironmentPostProcessor {
        @Override
        protected String overwriteName(ConfigurableEnvironment environment, SpringApplication application) {
            return "datasource";
        }

        @Override
        protected String[] overwriteLocationsPrefix(ConfigurableEnvironment environment, SpringApplication application) {
            return new String[]{
                    JarDir.of().getDirAsResourceLocation("config/datasource"),
                    JarDir.of().getDirAsResourceLocation(".config/datasource"),
                    JarDir.of().getDirAsResourceLocation("_config/datasource"),
                    JarDir.of().getDirAsResourceLocation("datasource"),
                    "file:config/datasource",
                    "file:.config/datasource",
                    "file:_config/datasource",
                    "file:datasource",
                    "classpath:config/datasource",
                    "classpath:.config/datasource",
                    "classpath:_config/datasource",
                    "classpath:datasource",
                    "classpath:META-INF/datasource",
                    getMainClassPrefix(application, "datasource")
            };
        }
    }

    public static class Git extends AbstractConventionEnvironmentPostProcessor {
        @Override
        protected String overwriteName(ConfigurableEnvironment environment, SpringApplication application) {
            return "git";
        }

        @Override
        protected String[] overwriteLocationsPrefix(ConfigurableEnvironment environment, SpringApplication application) {
            return new String[]{"classpath:git", "classpath:META-INF/git"};
        }
    }

}
