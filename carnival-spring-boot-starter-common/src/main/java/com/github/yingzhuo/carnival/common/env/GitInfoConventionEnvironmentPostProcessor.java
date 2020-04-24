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

import com.github.yingzhuo.springboot.env.support.AbstractConventionEnvironmentPostProcessor;

/**
 * @author 应卓
 * @since 1.4.2
 */
public class GitInfoConventionEnvironmentPostProcessor extends AbstractConventionEnvironmentPostProcessor {

    public GitInfoConventionEnvironmentPostProcessor() {
        super("git-info", new String[]{
                "classpath:config/git",
                "classpath:git",
                "classpath:META-INF/git",
        });
    }
}
