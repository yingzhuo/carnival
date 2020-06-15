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

import java.io.Serializable;

/**
 * @author 应卓
 * @since 1.6.18
 */
public interface ConfigHolder extends Serializable {

    public static final String DEFAULT_BACKEND_NAME = "default";

    public static ConfigHolderBuilder builder() {
        return new ConfigHolderBuilder();
    }

    public Object get(String backend, Module module);

    public void clear();

    public int size();

    public default Object getDefault(Module module) {
        return get(DEFAULT_BACKEND_NAME, module);
    }

    public default CircuitBreakerConfig getCircuitBreakerConfig(String backend) {
        return (CircuitBreakerConfig) get(backend, Module.CIRCUIT_BREAKER);
    }

    public default CircuitBreakerConfig getDefaultCircuitBreakerConfig() {
        return (CircuitBreakerConfig) get(DEFAULT_BACKEND_NAME, Module.CIRCUIT_BREAKER);
    }

    public default BulkheadConfig getBulkheadConfig(String backend) {
        return (BulkheadConfig) get(backend, Module.BULKHEAD);
    }

    public default BulkheadConfig getDefaultBulkheadConfig() {
        return (BulkheadConfig) get(DEFAULT_BACKEND_NAME, Module.BULKHEAD);
    }

    public default RetryConfig getRetryConfig(String backend) {
        return (RetryConfig) get(backend, Module.RETRY);
    }

    public default RetryConfig getDefaultRetryConfig() {
        return (RetryConfig) get(DEFAULT_BACKEND_NAME, Module.RETRY);
    }

    public default RateLimiterConfig getRateLimiterConfig(String backend) {
        return (RateLimiterConfig) get(backend, Module.RATE_LIMITER);
    }

    public default RateLimiterConfig getDefaultRateLimiterConfig() {
        return (RateLimiterConfig) get(DEFAULT_BACKEND_NAME, Module.RATE_LIMITER);
    }

}
