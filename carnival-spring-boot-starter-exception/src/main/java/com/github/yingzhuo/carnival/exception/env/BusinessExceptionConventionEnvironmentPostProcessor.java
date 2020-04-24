/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.exception.env;

import com.github.yingzhuo.springboot.env.support.AbstractConventionEnvironmentPostProcessor;
import com.github.yingzhuo.springboot.env.util.JarDir;

/**
 * @author 应卓
 * @since 1.4.2
 */
public class BusinessExceptionConventionEnvironmentPostProcessor extends AbstractConventionEnvironmentPostProcessor {

    public BusinessExceptionConventionEnvironmentPostProcessor() {
        super("business-exception", new String[]{
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
        });
    }

}
