/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.feign.env;

import com.github.yingzhuo.springboot.env.support.AbstractConventionEnvironmentPostProcessor;
import com.github.yingzhuo.springboot.env.util.JarDir;
import org.springframework.boot.SpringApplication;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @author 应卓
 * @since 1.6.0
 */
public class ServiceConventionEnvironmentPostProcessor extends AbstractConventionEnvironmentPostProcessor {

    @Override
    protected String getName(ConfigurableEnvironment environment, SpringApplication application) {
        return "service";
    }

    @Override
    protected String[] getLocationsPrefix(ConfigurableEnvironment environment, SpringApplication application) {
        final Class<?> mainClass = application.getMainApplicationClass();
        final String inMainClassPkg = "classpath:" + mainClass.getPackage().getName().replaceAll("\\.", "/") + "/service";

        return new String[]{
                JarDir.of(mainClass).getDirAsResourceLocation("config/service"),
                JarDir.of(mainClass).getDirAsResourceLocation(".config/service"),
                JarDir.of(mainClass).getDirAsResourceLocation("_config/service"),
                JarDir.of(mainClass).getDirAsResourceLocation("service"),
                "file:config/service",
                "file:.config/service",
                "file:_config/service",
                "file:service",
                "classpath:config/service",
                "classpath:.config/service",
                "classpath:_config/service",
                "classpath:service",
                "classpath:META-INF/service",
                inMainClassPkg
        };
    }

}
