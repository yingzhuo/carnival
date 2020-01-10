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
public class ProjectEnvironmentPostProcessor extends AbstractConventionEnvironmentPostProcessor {

    private static final String[] DEFAULT_PREFIX = {
            "classpath:project",
            "classpath:META-INF/project"
    };

    private static final String NAME = "project";

    public ProjectEnvironmentPostProcessor() {
        super(DEFAULT_PREFIX, NAME);
    }

}
