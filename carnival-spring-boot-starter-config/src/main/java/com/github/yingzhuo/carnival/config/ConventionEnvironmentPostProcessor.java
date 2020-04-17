/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.config;

import com.github.yingzhuo.carnival.config.support.AbstractConventionEnvironmentPostProcessor;
import com.github.yingzhuo.carnival.config.util.JarLocation;

/**
 * @author 应卓
 * @since 1.4.1
 */
public class ConventionEnvironmentPostProcessor extends AbstractConventionEnvironmentPostProcessor {

    public ConventionEnvironmentPostProcessor() {
        super(new String[]{
                JarLocation.of().getFileAsResourceLocation("config/property"),
                JarLocation.of().getFileAsResourceLocation("property"),
                "file:config/property",
                "file:property",
                "classpath:config/property",
                "classpath:property",
                "classpath:META-INF/property",
        }, "property-source");
    }

}
