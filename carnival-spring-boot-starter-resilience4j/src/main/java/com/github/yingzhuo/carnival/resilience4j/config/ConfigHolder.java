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

import java.util.List;

/**
 * @author 应卓
 * @since 1.6.18
 */
public interface ConfigHolder {

    public static ConfigHolderBuilder builder() {
        return new ConfigHolderBuilder();
    }

    public static ConfigHolder empty() {
        return new ConfigHolderBuilder.Empty();
    }

    public Object getModuleConfig(String backend, Module module);

    public List<FallbackConfig> getFallbackConfig(String backend);

    public void clear();

    public int size();

    public default boolean isEmpty() {
        return size() == 0;
    }

    // ----------------------------------------------------------------------------------------------------------------

    public default CircuitBreakerConfig getCircuitBreakerConfig(String backend) {
        return (CircuitBreakerConfig) getModuleConfig(backend, Module.CIRCUIT_BREAKER);
    }

    public default BulkheadConfig getBulkheadConfig(String backend) {
        return (BulkheadConfig) getModuleConfig(backend, Module.BULKHEAD);
    }

    public default RetryConfig getRetryConfig(String backend) {
        return (RetryConfig) getModuleConfig(backend, Module.RETRY);
    }

    public default RateLimiterConfig getRateLimiterConfig(String backend) {
        return (RateLimiterConfig) getModuleConfig(backend, Module.RATE_LIMITER);
    }

}
