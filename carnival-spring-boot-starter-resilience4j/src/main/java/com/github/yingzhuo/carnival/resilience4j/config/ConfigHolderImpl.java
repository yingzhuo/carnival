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

import com.github.yingzhuo.carnival.common.Null;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import io.github.resilience4j.bulkhead.BulkheadConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.retry.RetryConfig;
import org.springframework.util.Assert;

import java.util.Objects;

/**
 * @author 应卓
 * @since 1.6.18
 */
class ConfigHolderImpl implements ConfigHolder {

    private static final Object NULL = Null.INSTANCE;

    private final Table<String, Module, Object> configTable = HashBasedTable.create();

    public ConfigHolderImpl() {
    }

    @Override
    public void clear() {
        configTable.clear();
    }

    @Override
    public int size() {
        return configTable.size();
    }

    @Override
    public Object get(String backend, Module module) {
        Assert.hasText(backend, "backend is null or empty");
        Assert.notNull(module, "module is null");

        Object obj = configTable.get(
                Objects.requireNonNull(backend),
                Objects.requireNonNull(module)
        );

        return obj == NULL ? null : obj;
    }

    public void put(String backend, CircuitBreakerConfig config) {
        Assert.hasText(backend, "backend is null or empty");
        Assert.notNull(config, "config is null");

        configTable.put(
                Objects.requireNonNull(backend),
                Module.CIRCUIT_BREAKER,
                config == null ? NULL : config
        );
    }

    public void put(String backend, BulkheadConfig config) {
        Assert.hasText(backend, "backend is null or empty");
        Assert.notNull(config, "config is null");

        configTable.put(
                Objects.requireNonNull(backend),
                Module.BULKHEAD,
                config == null ? NULL : config
        );
    }

    public void put(String backend, RetryConfig config) {
        Assert.hasText(backend, "backend is null or empty");
        Assert.notNull(config, "config is null");

        configTable.put(
                Objects.requireNonNull(backend),
                Module.RETRY,
                config == null ? NULL : config
        );
    }

    public void put(String backend, RateLimiterConfig config) {
        Assert.hasText(backend, "backend is null or empty");
        Assert.notNull(config, "config is null");

        configTable.put(
                Objects.requireNonNull(backend),
                Module.RATE_LIMITER,
                config == null ? NULL : config
        );
    }

}
