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

/**
 * @author 应卓
 * @since 1.5.2
 */
public class ServiceConventionEnvironmentPostProcessor extends AbstractConventionEnvironmentPostProcessor {

    public ServiceConventionEnvironmentPostProcessor() {
        super("service", new String[]{
                JarDir.of().getDirAsResourceLocation("config/service"),
                JarDir.of().getDirAsResourceLocation("config/svc"),
                JarDir.of().getDirAsResourceLocation("service"),
                JarDir.of().getDirAsResourceLocation("svc"),
                "file:config/service",
                "file:config/svc",
                "file:service",
                "file:svc",
                "classpath:config/service",
                "classpath:config/svc",
                "classpath:service",
                "classpath:svc",
                "classpath:META-INF/service",
                "classpath:META-INF/svc",
        });
    }
}
