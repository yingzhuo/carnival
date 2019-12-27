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
            "file:config/property-source",
            "file:property-source",
            "classpath:/config/property-source",
            "classpath:/property-source",
            "classpath:/META-INF/property-source",
    };

    public OnBuildPropertiesEnvironmentPostProcessor() {
        super(ONBUILD_PROPERTIES_LOCATIONS, new String[]{".properties"}, new PropertiesPropertySourceLoader());
    }

}
