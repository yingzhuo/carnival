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

/**
 * @author 应卓
 * @since 1.4.1
 */
public class ConventionEnvironmentPostProcessor extends AbstractConventionEnvironmentPostProcessor {

    private static final String[] DEFAULT_PREFIX = {
            "file:config/property",
            "file:config/property-source",
            "file:property",
            "file:property-source",
            "classpath:config/property",
            "classpath:config/property-source",
            "classpath:property",
            "classpath:property-source",
            "classpath:META-INF/property",
            "classpath:META-INF/property-source",
    };

    private static final String NAME = "property-source";

    public ConventionEnvironmentPostProcessor() {
        super(getPrefix(), NAME);
    }

    private static String[] getPrefix() {
        String envValue = System.getenv("CONVENTION_PROPERTY_SOURCE");

        if (envValue == null || envValue.trim().isEmpty()) {
            return DEFAULT_PREFIX;
        }

        String[] prefix = envValue.split(",");
        for (int i = 0; i < prefix.length; i++) {
            prefix[i] = prefix[i].trim();
        }

        return prefix;
    }

}
