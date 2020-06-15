/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.resilience4j.util;

import com.github.yingzhuo.carnival.resilience4j.config.ConfigHolder;
import io.github.resilience4j.bulkhead.Bulkhead;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.feign.FeignDecorators;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.retry.Retry;

import java.util.Objects;
import java.util.Optional;

/**
 * @author 应卓
 * @since 1.6.18
 */
public final class FeignDecoratorsUtils {

    private FeignDecoratorsUtils() {
    }

    public static FeignDecorators getDefaultDecorators(final ConfigHolder holder) {
        return getDecorators(ConfigHolder.DEFAULT_BACKEND_NAME, holder);
    }

    public static FeignDecorators getDecorators(final String backend, final ConfigHolder holder) {
        Objects.requireNonNull(backend);
        Objects.requireNonNull(holder);

        final FeignDecorators.Builder builder = FeignDecorators.builder();

        Optional<Retry> retryOption =
                Optional.ofNullable(holder.getRetryConfig(backend))
                        .map(cnf -> Retry.of(backend, cnf));

        Optional<CircuitBreaker> circuitBreakerOption =
                Optional.ofNullable(holder.getCircuitBreakerConfig(backend))
                        .map(cnf -> CircuitBreaker.of(backend, cnf));

        Optional<RateLimiter> rateLimiterOption =
                Optional.ofNullable(holder.getRateLimiterConfig(backend))
                        .map(cnf -> RateLimiter.of(backend, cnf));

        Optional<Bulkhead> bulkheadOption =
                Optional.ofNullable(holder.getBulkheadConfig(backend))
                        .map(cnf -> Bulkhead.of(backend, cnf));

        bulkheadOption.ifPresent(builder::withBulkhead); // 最内层装饰器
        rateLimiterOption.ifPresent(builder::withRateLimiter);
        circuitBreakerOption.ifPresent(builder::withCircuitBreaker);
        retryOption.ifPresent(builder::withRetry);  // 最外层装饰器
        return builder.build();
    }

}
