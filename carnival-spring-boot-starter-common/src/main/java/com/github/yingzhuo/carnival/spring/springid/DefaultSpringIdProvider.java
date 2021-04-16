/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.spring.springid;

import org.springframework.core.env.Environment;

/**
 * @author 应卓
 * @since 1.8.3
 */
public class DefaultSpringIdProvider implements SpringIdProvider {

    private final Environment env;

    public DefaultSpringIdProvider(Environment env) {
        this.env = env;
    }

    @Override
    public String get() {
        return env.getProperty("spring.id", String.class);
    }

}
