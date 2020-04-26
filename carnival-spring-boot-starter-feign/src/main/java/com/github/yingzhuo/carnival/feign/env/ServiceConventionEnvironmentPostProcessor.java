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

/**
 * @author 应卓
 * @since 1.6.0
 */
public class ServiceConventionEnvironmentPostProcessor extends AbstractConventionEnvironmentPostProcessor {

    public ServiceConventionEnvironmentPostProcessor() {
        super("service", new String[]{
                JarDir.of().getDirAsResourceLocation("config/service"),
                JarDir.of().getDirAsResourceLocation(".config/service"),
                JarDir.of().getDirAsResourceLocation("_config/service"),
                JarDir.of().getDirAsResourceLocation("service"),
                "file:config/service",
                "file:.config/service",
                "file:_config/service",
                "file:service",
                "classpath:config/service",
                "classpath:.config/service",
                "classpath:_config/service",
                "classpath:service",
                "classpath:META-INF/service"
        });
    }
}
