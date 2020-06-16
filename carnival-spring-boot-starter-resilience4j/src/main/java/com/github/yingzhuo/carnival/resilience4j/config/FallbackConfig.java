/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.resilience4j.config;

/**
 * @author 应卓
 * @since 1.6.8
 */
public final class FallbackConfig {

    private final String backend;
    private final FallbackConfigType type;
    private final Object[] args;

    public FallbackConfig(String backend, FallbackConfigType type, Object... args) {
        this.type = type;
        this.args = args;
        this.backend = backend;
    }

    public String getBackend() {
        return backend;
    }

    public FallbackConfigType getType() {
        return type;
    }

    public Object[] getArgs() {
        return args;
    }

}