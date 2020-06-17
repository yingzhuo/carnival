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

import lombok.Getter;

import java.util.Arrays;

/**
 * @author 应卓
 * @since 1.6.8
 */
@Getter
public final class FallbackConfig {

    private final String backend;
    private final FallbackConfigType type;
    private final Object[] args;

    public FallbackConfig(String backend, FallbackConfigType type, Object... args) {
        this.type = type;
        this.args = args;
        this.backend = backend;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FallbackConfig that = (FallbackConfig) o;

        if (!backend.equals(that.backend)) return false;
        if (type != that.type) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(args, that.args);
    }

    @Override
    public int hashCode() {
        int result = backend.hashCode();
        result = 31 * result + type.hashCode();
        result = 31 * result + Arrays.hashCode(args);
        return result;
    }

}
