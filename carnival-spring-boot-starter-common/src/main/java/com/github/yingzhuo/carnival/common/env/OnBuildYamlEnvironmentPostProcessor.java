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

import org.springframework.boot.env.YamlPropertySourceLoader;

/**
 * @author 应卓
 * @since 1.3.7
 */
public class OnBuildYamlEnvironmentPostProcessor extends AbstractOnBuildEnvironmentPostProcessor {

    private static final String[] ONBUILD_YAML_LOCATIONS = new String[]{
            "file:config/property-source.yml",
            "file:config/property-source.yaml",
            "file:property-source.yml",
            "file:property-source.yaml",
            "classpath:/config/property-source.yml",
            "classpath:/config/property-source.yaml",
            "classpath:/property-source.yml",
            "classpath:/property-source.yaml",
            "classpath:/META-INF/property-source.yml",
            "classpath:/META-INF/property-source.yaml",
    };

    public OnBuildYamlEnvironmentPostProcessor() {
        super(ONBUILD_YAML_LOCATIONS, new YamlPropertySourceLoader());
    }

}
