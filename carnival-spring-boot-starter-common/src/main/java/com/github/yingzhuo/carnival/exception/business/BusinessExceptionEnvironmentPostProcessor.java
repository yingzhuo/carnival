/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.exception.business;

import com.github.yingzhuo.carnival.config.env.AbstractConventionEnvironmentPostProcessor;
import com.github.yingzhuo.carnival.config.util.JarDir;
import org.springframework.boot.SpringApplication;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @author 应卓
 * @since 1.6.15
 */
public class BusinessExceptionEnvironmentPostProcessor extends AbstractConventionEnvironmentPostProcessor {

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
