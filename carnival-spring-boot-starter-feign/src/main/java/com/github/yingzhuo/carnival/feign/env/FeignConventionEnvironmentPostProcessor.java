/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.feign.env;

import org.springframework.boot.env.support.AbstractConventionEnvironmentPostProcessor;

/**
 * @author 应卓
 * @since 1.4.1
 */
public class FeignConventionEnvironmentPostProcessor extends AbstractConventionEnvironmentPostProcessor {

    private static final String[] PREFIX = {
            "file:config/datasource",
            "file:datasource",
            "classpath:config/datasource",
            "classpath:datasource",
            "classpath:META-INF/datasource",
    };

    public FeignConventionEnvironmentPostProcessor() {
        super(PREFIX, "feign");
    }

}
