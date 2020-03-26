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
 * @since 1.4.9
 */
public class GitEnvironmentPostProcessor extends AbstractConventionEnvironmentPostProcessor {

    public GitEnvironmentPostProcessor() {
        super(new String[]{"classpath:config/git", "classpath:git", "classpath:META-INF/git"}, "git-info");
    }

}
