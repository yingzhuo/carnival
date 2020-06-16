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
public class FallbackConfig {

    private FallbackConfigType type;

    private Object[] args;

    public FallbackConfig(FallbackConfigType type, Object... args) {
        this.type = type;
        this.args = args;
    }

    public FallbackConfigType getType() {
        return type;
    }

    public void setType(FallbackConfigType type) {
        this.type = type;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }
}
