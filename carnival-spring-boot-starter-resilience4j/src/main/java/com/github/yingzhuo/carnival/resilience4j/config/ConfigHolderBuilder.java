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

import io.github.resilience4j.bulkhead.BulkheadConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.retry.RetryConfig;
import org.springframework.util.Assert;

/**
 * @author 应卓
 * @since 1.6.18
 */
public final class ConfigHolderBuilder {

    private final ConfigHolderImpl impl = new ConfigHolderImpl();

    ConfigHolderBuilder() {
    }

    public ConfigHolderBuilder circuitBreaker(String backend, CircuitBreakerConfig config) {
        Assert.hasText(backend, "backend is null or empty");
        Assert.notNull(config, "config is null");
        impl.put(backend, config);
        return this;
    }

    public ConfigHolderBuilder bulkhead(String backend, BulkheadConfig config) {
        Assert.hasText(backend, "backend is null or empty");
        Assert.notNull(config, "config is null");
        impl.put(backend, config);
        return this;
    }

    public ConfigHolderBuilder retry(String backend, RetryConfig config) {
        Assert.hasText(backend, "backend is null or empty");
        Assert.notNull(config, "config is null");
        impl.put(backend, config);
        return this;
    }

    public ConfigHolderBuilder rateLimiter(String backend, RateLimiterConfig config) {
        Assert.hasText(backend, "backend is null or empty");
        Assert.notNull(config, "config is null");
        impl.put(backend, config);
        return this;
    }

    public ConfigHolder build() {
        return impl;
    }

}
