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

import com.github.yingzhuo.carnival.config.support.AbstractConventionEnvironmentPostProcessor;

/**
 * @author 应卓
 * @since 1.4.2
 */
public class DataSourceConventionEnvironmentPostProcessor extends AbstractConventionEnvironmentPostProcessor {

    private static final String[] DEFAULT_PREFIX = {
            "file:config/datasource",
            "file:datasource",
            "classpath:config/datasource",
            "classpath:datasource",
            "classpath:META-INF/datasource",
    };

    private static final String NAME = "datasource";

    public DataSourceConventionEnvironmentPostProcessor() {
        super(DEFAULT_PREFIX, NAME);
    }
}
