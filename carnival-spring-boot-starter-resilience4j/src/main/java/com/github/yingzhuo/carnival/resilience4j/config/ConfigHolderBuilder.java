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
import com.github.yingzhuo.carnival.resilience4j.util.FallbackConditions;
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
public final class ConfigHolderBuilder {

    private final Impl impl = new Impl();

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

    public ConfigHolderBuilder addFallback(String backend, Object fallback) {
        return addFallback(backend, fallback, FallbackConditions.all());
    }

    @SafeVarargs
    public final ConfigHolderBuilder addFallback(String backend, Object fallback, Predicate<? extends Exception>... filters) {
        Assert.noNullElements(filters, "filters is null or has null element(s)");

        for (Predicate<? extends Exception> filter : filters) {
            doAddFallback(backend, fallback, filter);
        }
        return this;
    }

    private void doAddFallback(String backend, Object fallback, Predicate<? extends Exception> filter) {
        Assert.hasText(backend, "backend is null or empty");
        Assert.notNull(fallback, "fallback is null");
        impl.addFallback(backend, fallback, filter);
    }

    @SafeVarargs
    public final ConfigHolderBuilder addFallback(String backend, Object fallback, Class<? extends Exception>... filters) {
        Assert.noNullElements(filters, "filters is null or has null element(s)");
        for (Class<? extends Exception> filter : filters) {
            doAddFallback(backend, fallback, filter);
        }
        return this;
    }

    private void doAddFallback(String backend, Object fallback, Class<? extends Exception> filter) {
        Assert.hasText(backend, "backend is null or empty");
        Assert.notNull(fallback, "fallback is null");
        impl.addFallback(backend, fallback, filter);
    }

    public ConfigHolder build() {
        return impl;
    }

    // 实现类
    // 不是线程安全的，也不需要线程安全
    static class Impl implements ConfigHolder {
        private static final Object NULL = Null.INSTANCE;

        private final Table<String, Module, Object> moduleConfigTable = HashBasedTable.create();
        private final Map<String, List<FallbackConfig>> fallbackConfigDict = new HashMap<>();
        private int size = 0;

        private Impl() {
        }

        @Override
        public Object getModuleConfig(String backend, Module module) {
            Object obj = moduleConfigTable.get(
                    Objects.requireNonNull(backend),
                    Objects.requireNonNull(module)
            );
            return obj == NULL ? null : obj;
        }

        @Override
        public List<FallbackConfig> getFallbackConfig(String backend) {
            List<FallbackConfig> list = fallbackConfigDict.get(backend);
            return list != null ? Collections.unmodifiableList(list) : Collections.emptyList();
        }

        @Override
        public int size() {
            return this.size;
        }

        @Override
        public void clear() {
            fallbackConfigDict.clear();
            moduleConfigTable.clear();
            size = 0;
        }

        public void put(String backend, CircuitBreakerConfig config) {
            moduleConfigTable.put(
                    Objects.requireNonNull(backend),
                    Module.CIRCUIT_BREAKER,
                    config == null ? NULL : config
            );
            size++;
        }

        public void put(String backend, BulkheadConfig config) {
            moduleConfigTable.put(
                    Objects.requireNonNull(backend),
                    Module.BULKHEAD,
                    config == null ? NULL : config
            );
            size++;
        }

        public void put(String backend, RetryConfig config) {
            moduleConfigTable.put(
                    Objects.requireNonNull(backend),
                    Module.RETRY,
                    config == null ? NULL : config
            );
            size++;
        }

        public void put(String backend, RateLimiterConfig config) {
            moduleConfigTable.put(
                    Objects.requireNonNull(backend),
                    Module.RATE_LIMITER,
                    config == null ? NULL : config
            );
            size++;
        }

        public void addFallback(String backend, Object fallback, Predicate<? extends Exception> filter) {
            FallbackConfigType type = FallbackConfigType.FALLBACK_WITH_PREDICATE;
            FallbackConfig fallbackConfig = new FallbackConfig(backend, type, fallback, filter);

            List<FallbackConfig> list = fallbackConfigDict.computeIfAbsent(backend, k -> new LinkedList<>());
            list.add(fallbackConfig);
            size++;
        }

        public void addFallback(String backend, Object fallback, Class<? extends Exception> filter) {
            FallbackConfigType type = FallbackConfigType.FALLBACK_WITH_EXCEPTION_CLASS;
            FallbackConfig fallbackConfig = new FallbackConfig(backend, type, fallback, filter);

            List<FallbackConfig> list = fallbackConfigDict.computeIfAbsent(backend, k -> new LinkedList<>());
            list.add(fallbackConfig);
            size++;
        }
    }

    static final class Empty implements ConfigHolder {
        @Override
        public Object getModuleConfig(String backend, Module module) {
            return null;
        }

        @Override
        public List<FallbackConfig> getFallbackConfig(String backend) {
            return new ArrayList<>();
        }

        @Override
        public void clear() {
        }

        @Override
        public int size() {
            return 0;
        }
    }
}
