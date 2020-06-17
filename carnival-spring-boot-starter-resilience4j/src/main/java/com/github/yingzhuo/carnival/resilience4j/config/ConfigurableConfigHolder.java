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

import java.util.function.Predicate;

/**
 * @author 应卓
 * @since 1.6.19
 */
public interface ConfigurableConfigHolder extends ConfigHolder {

    public void set(String backend, CircuitBreakerConfig config);

    public void set(String backend, BulkheadConfig config);

    public void set(String backend, RetryConfig config);

    public void set(String backend, RateLimiterConfig config);

    public void addFallback(String backend, Object fallback, Predicate<? extends Exception> filter);

    public void addFallback(String backend, Object fallback, Class<? extends Exception> filter);

}
