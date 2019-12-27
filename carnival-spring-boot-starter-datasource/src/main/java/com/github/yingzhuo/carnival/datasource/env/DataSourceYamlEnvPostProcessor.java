/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.datasource.env;

import com.github.yingzhuo.carnival.common.env.AbstractOnBuildEnvironmentPostProcessor;
import org.springframework.boot.env.YamlPropertySourceLoader;

/**
 * @author 应卓
 * @since 1.3.8
 */
public class DataSourceYamlEnvPostProcessor extends AbstractOnBuildEnvironmentPostProcessor {

    private static final String[] ONBUILD_PROPERTIES_LOCATIONS = new String[]{
            "file:config/datasource",
            "file:datasource",
            "classpath:/config/datasource",
            "classpath:/datasource",
            "classpath:/META-INF/datasource",
    };

    public DataSourceYamlEnvPostProcessor() {
        super(ONBUILD_PROPERTIES_LOCATIONS, new String[]{".yml", ".yaml"}, new YamlPropertySourceLoader());
    }

}
