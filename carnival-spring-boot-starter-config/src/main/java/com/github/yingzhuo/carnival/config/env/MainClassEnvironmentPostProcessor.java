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

import org.springframework.boot.SpringApplication;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @author 应卓
 * @since 1.6.15
 */
public class MainClassEnvironmentPostProcessor extends AbstractConventionEnvironmentPostProcessor {

    @Override
    protected String getName(ConfigurableEnvironment environment, SpringApplication application) {
        return "main-class";
    }

    @Override
    protected String[] getLocationsPrefix(ConfigurableEnvironment environment, SpringApplication application) {
        final Class<?> mainClass = application.getMainApplicationClass();

        // 疑似是Spring的bug
        if (mainClass == null) {
            return new String[0];
        }

        return new String[]{
                "classpath:" + mainClass.getPackage().getName().replaceAll("\\.", "/") + "/" + mainClass.getSimpleName()
        };
    }

}
