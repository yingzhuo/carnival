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

import com.github.yingzhuo.carnival.common.env.AbstractEnvironmentPostProcessor;
import org.springframework.boot.env.YamlPropertySourceLoader;

/**
 * @author 应卓
 * @since 1.4.0
 */
public class DataSourceExtendEnvironmentPostProcessor extends AbstractEnvironmentPostProcessor {

    private static final String[] prefix = new String[]{
            "file:config/datasource",
            "file:datasource",
            "classpath:config/datasource",
            "classpath:datasource",
            "classpath:META-INF/datasource",
    };

    private static final String[] suffix = new String[]{
            ".yml",
            ".yaml",
    };

    public DataSourceExtendEnvironmentPostProcessor() {
        super(prefix,  suffix,"carnival-datasource", new YamlPropertySourceLoader());
    }
}
