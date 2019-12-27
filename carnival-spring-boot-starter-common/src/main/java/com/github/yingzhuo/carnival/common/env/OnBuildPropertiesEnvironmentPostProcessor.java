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

import org.springframework.boot.env.PropertiesPropertySourceLoader;

/**
 * @author 应卓
 * @since 1.3.7
 */
public class OnBuildPropertiesEnvironmentPostProcessor extends AbstractOnBuildEnvironmentPostProcessor {

    private static final String[] ONBUILD_PROPERTIES_LOCATIONS = new String[]{
            "file:config/property-source.properties",
            "file:property-source.properties",
            "classpath:/config/property-source.properties",
            "classpath:/property-source.properties",
            "classpath:/META-INF/property-source.properties",
    };

    public OnBuildPropertiesEnvironmentPostProcessor() {
        super(ONBUILD_PROPERTIES_LOCATIONS, new PropertiesPropertySourceLoader());
    }
}
