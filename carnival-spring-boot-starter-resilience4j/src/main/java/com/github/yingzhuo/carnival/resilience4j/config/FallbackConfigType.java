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
 * @since 1.6.18
 */
public enum FallbackConfigType {

    TYPE1("withFallback(Object fallback)"),

    TYPE2("withFallback(Object fallback, Predicate<Exception> filter)"),

    TYPE3("withFallback(Object fallback, Class<Exception> filter)");

    private final String description;

    FallbackConfigType(String description) {
        this.description = description;
    }

    // just for logging
    public String getDescription() {
        return description;
    }

}
