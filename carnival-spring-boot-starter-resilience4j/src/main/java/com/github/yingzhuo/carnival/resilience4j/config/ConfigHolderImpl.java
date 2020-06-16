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

import java.util.*;
import java.util.function.Predicate;

/**
 * @author 应卓
 * @since 1.6.18
 */
public class ConfigHolderImpl implements ConfigHolder {

    private static final Object NULL = Null.INSTANCE;

    private final Table<String, Module, Object> moduleConfigTable = HashBasedTable.create();
    private final Map<String, List<FallbackConfig>> fallbackConfigDict = new HashMap<>();

    public ConfigHolderImpl() {
    }

    @Override
    public Object getModuleConfig(String backend, Module module) {
        Assert.hasText(backend, "backend is null or empty");
        Assert.notNull(module, "module is null");

        Object obj = moduleConfigTable.get(
                Objects.requireNonNull(backend),
                Objects.requireNonNull(module)
        );

        return obj == NULL ? null : obj;
    }

    @Override
    public List<FallbackConfig> getFallbackConfig(String backend) {
        Assert.hasText(backend, "backend is null or empty");

        List<FallbackConfig> list = fallbackConfigDict.get(backend);
        return list != null ? Collections.unmodifiableList(list) : Collections.emptyList();
    }

    public void put(String backend, CircuitBreakerConfig config) {
        Assert.hasText(backend, "backend is null or empty");
        Assert.notNull(config, "config is null");

        moduleConfigTable.put(
                Objects.requireNonNull(backend),
                Module.CIRCUIT_BREAKER,
                config == null ? NULL : config
        );
    }

    public void put(String backend, BulkheadConfig config) {
        Assert.hasText(backend, "backend is null or empty");
        Assert.notNull(config, "config is null");

        moduleConfigTable.put(
                Objects.requireNonNull(backend),
                Module.BULKHEAD,
                config == null ? NULL : config
        );
    }

    public void put(String backend, RetryConfig config) {
        Assert.hasText(backend, "backend is null or empty");
        Assert.notNull(config, "config is null");

        moduleConfigTable.put(
                Objects.requireNonNull(backend),
                Module.RETRY,
                config == null ? NULL : config
        );
    }

    public void put(String backend, RateLimiterConfig config) {
        Assert.hasText(backend, "backend is null or empty");
        Assert.notNull(config, "config is null");

        moduleConfigTable.put(
                Objects.requireNonNull(backend),
                Module.RATE_LIMITER,
                config == null ? NULL : config
        );
    }

    public void addFallback(String backend, Object fallback) {
        Assert.hasText(backend, "backend is null or empty");
        Assert.notNull(fallback, "fallback is null");

        FallbackConfigType type = FallbackConfigType.TYPE1;
        FallbackConfig fallbackConfig = new FallbackConfig(type, fallback);

        List<FallbackConfig> list = fallbackConfigDict.get(backend);
        if (list == null) {
            list = new LinkedList<>();
            fallbackConfigDict.put(backend, list);
        }
        list.add(fallbackConfig);
    }

    public void addFallback(String backend, Object fallback, Predicate<? extends Exception> filter) {
        Assert.hasText(backend, "backend is null or empty");
        Assert.notNull(fallback, "fallback is null");
        Assert.notNull(filter, "filter is null");

        FallbackConfigType type = FallbackConfigType.TYPE2;
        FallbackConfig fallbackConfig = new FallbackConfig(type, fallback, filter);

        List<FallbackConfig> list = fallbackConfigDict.get(backend);
        if (list == null) {
            list = new LinkedList<>();
            fallbackConfigDict.put(backend, list);
        }
        list.add(fallbackConfig);
    }

    public void addFallback(String backend, Object fallback, Class<? extends Exception> filter) {
        Assert.hasText(backend, "backend is null or empty");
        Assert.notNull(fallback, "fallback is null");
        Assert.notNull(filter, "filter is null");

        FallbackConfigType type = FallbackConfigType.TYPE3;
        FallbackConfig fallbackConfig = new FallbackConfig(type, fallback, filter);

        List<FallbackConfig> list = fallbackConfigDict.get(backend);
        if (list == null) {
            list = new LinkedList<>();
            fallbackConfigDict.put(backend, list);
        }
        list.add(fallbackConfig);
    }

}
