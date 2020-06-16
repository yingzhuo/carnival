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
import com.github.yingzhuo.carnival.resilience4j.config.FallbackConfig;
import io.github.resilience4j.bulkhead.Bulkhead;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.feign.FeignDecorators;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.retry.Retry;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * @author 应卓
 * @since 1.6.18
 */
public final class FeignDecoratorsUtils {

    private FeignDecoratorsUtils() {
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

        // fallback and fallbackFactory
        setFallback(builder, holder.getFallbackConfig(backend));

        return builder.build();
    }

    @SuppressWarnings("unchecked")
    private static void setFallback(FeignDecorators.Builder builder, List<FallbackConfig> fallbackConfigList) {
        if (fallbackConfigList != null) {
            for (FallbackConfig cnf : fallbackConfigList) {
                switch (cnf.getType()) {
                    case FALLBACK_WITH_PREDICATE:
                        builder.withFallback(
                                cnf.getArgs()[0],
                                (Predicate<Exception>) cnf.getArgs()[1]
                        );
                        break;
                    case FALLBACK_WITH_EXCEPTION_CLASS:
                        builder.withFallback(
                                cnf.getArgs()[0],
                                (Class<? extends Exception>) cnf.getArgs()[1]
                        );
                        break;
                    default:
                        throw new AssertionError();
                }
            }
        }
    }

}
