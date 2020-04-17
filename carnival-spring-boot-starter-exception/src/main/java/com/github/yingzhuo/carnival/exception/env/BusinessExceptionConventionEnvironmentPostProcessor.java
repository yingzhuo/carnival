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

import com.github.yingzhuo.carnival.config.support.AbstractConventionEnvironmentPostProcessor;
import com.github.yingzhuo.carnival.config.util.JarLocation;

/**
 * @author 应卓
 * @since 1.4.2
 */
public class BusinessExceptionConventionEnvironmentPostProcessor extends AbstractConventionEnvironmentPostProcessor {

    public BusinessExceptionConventionEnvironmentPostProcessor() {
        super(new String[]{
                JarLocation.of().getFileAsResourceLocation("config/business-exception"),
                JarLocation.of().getFileAsResourceLocation("business-exception"),
                "file:config/business-exception",
                "file:business-exception",
                "classpath:config/business-exception",
                "classpath:business-exception",
                "classpath:META-INF/business-exception",
        }, "business-exception");
    }

}
