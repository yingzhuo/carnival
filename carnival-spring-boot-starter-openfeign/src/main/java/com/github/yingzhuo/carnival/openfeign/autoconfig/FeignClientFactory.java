/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.openfeign.autoconfig;

import com.github.yingzhuo.carnival.openfeign.FeignBuilderCustomizer;
import com.github.yingzhuo.carnival.openfeign.props.FeignProperties;
import com.github.yingzhuo.carnival.openfeign.resilience4j.*;
import com.github.yingzhuo.carnival.openfeign.target.CoreTarget;
import com.github.yingzhuo.carnival.openfeign.target.UrlSupplier;
import feign.*;
import feign.auth.BasicAuthRequestInterceptor;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.codec.ErrorDecoder;
import feign.slf4j.Slf4jLogger;
import io.github.resilience4j.bulkhead.BulkheadConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.feign.FeignDecorators;
import io.github.resilience4j.feign.Resilience4jFeign;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.retry.RetryConfig;
import lombok.val;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.time.Duration;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static feign.Feign.Builder;

/**
 * @author 应卓
 * @since 1.6.17
 */
class FeignClientFactory<T> implements FactoryBean<T>, ApplicationContextAware {

    private ApplicationContext applicationContext;

    // setter注入
    private Class<T> clientType;
    private Class<?> urlSupplierType;
    private String url;

    // 以下为初始化时才处理的字段
    private Builder builder = null;
    private FeignProperties props = null;

    @Override
    public T getObject() {
        initBuilder();
        return builder.target(CoreTarget.of(clientType, getUrlSupplier(urlSupplierType, url)));
    }

    @Override
    public Class<?> getObjectType() {
        return clientType;
    }

    private void initBuilder() {
        builder = createBasicBuilder();
        props = applicationContext.getBean(FeignProperties.class);

        initClient();
        initLogger();
        initInterceptors();
        initContract();
        initEncoder();
        initDecoder();
        initErrorDecoder();
        init404();
        initRetryer();
        initCapabilities();
        initOptions();
        initBasicAuth();
        initBearerAuth();
        reset();
    }

    private Builder createBasicBuilder() {

        val name = UUID.randomUUID().toString();
        val xBuilder = FeignDecorators.builder();

        findResilience4jAnnotation(Bulkhead.class).ifPresent(a -> {
            xBuilder.withBulkhead(
                    io.github.resilience4j.bulkhead.Bulkhead.of(
                            "BULKHEAD_" + name,
                            BulkheadConfig.custom()
                                    .maxConcurrentCalls(a.maxConcurrentCalls())
                                    .maxWaitDuration(Duration.ofMillis(a.maxWaitInMillis()))
                                    .build()
                    )
            );
        });

        findResilience4jAnnotation(RateLimiter.class).ifPresent(a -> {
            xBuilder.withRateLimiter(
                    io.github.resilience4j.ratelimiter.RateLimiter.of(
                            "RATE_LIMITER_" + name,
                            RateLimiterConfig.custom()
                                    .limitForPeriod(a.limitForPeriod())
                                    .limitRefreshPeriod(Duration.ofNanos(a.limitRefreshPeriodInNanos()))
                                    .timeoutDuration(Duration.ofSeconds(a.timeoutDurationInSeconds()))
                                    .build()
                    )
            );
        });

        findResilience4jAnnotation(CircuitBreaker.class).ifPresent(a -> {
            xBuilder.withCircuitBreaker(
                    io.github.resilience4j.circuitbreaker.CircuitBreaker.of(
                            "CIRCUIT_BREAKER_" + name,
                            CircuitBreakerConfig.custom()
                                    .recordExceptions(a.exceptionTypes())
                                    .ignoreExceptions(a.ignoreExceptionTypes())
                                    .permittedNumberOfCallsInHalfOpenState(a.permittedNumberOfCallsInHalfOpenState())
                                    .failureRateThreshold(a.failureRateThreshold())
                                    .slowCallRateThreshold(a.slowCallRateThreshold())
                                    .slowCallDurationThreshold(Duration.ofSeconds(a.slowCallDurationThresholdInSeconds()))
                                    .minimumNumberOfCalls(a.minimumNumberOfCalls())
                                    .slidingWindowSize(a.slidingWindowSize())
                                    .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)
                                    .build()
                    )
            );
        });

        findResilience4jAnnotation(Retry.class).ifPresent(a -> {
            xBuilder.withRetry(
                    io.github.resilience4j.retry.Retry.of(
                            "RETRY_" + name,
                            RetryConfig.custom()
                                    .maxAttempts(a.maxAttempts())
                                    .retryExceptions(a.exceptionTypes())
                                    .ignoreExceptions(a.ignoreExceptionTypes())
                                    .build()
                    )
            );
        });

        findResilience4jAnnotation(Fallback.class).ifPresent(a -> {
            val fallback = applicationContext.getBean(a.type());
            for (val exType : a.fallbackExceptions()) {
                xBuilder.withFallback(fallback, exType);
            }
        });

        return Resilience4jFeign.builder(xBuilder.build());
    }

    private <A extends Annotation> Optional<A> findResilience4jAnnotation(Class<A> annotationType) {
        return Optional.ofNullable(clientType.getAnnotation(annotationType));
    }

    private void initClient() {
        try {
            builder.client(applicationContext.getBean(Client.class));
        } catch (BeansException ignored) {
        }
    }

    private void initLogger() {
        builder.logger(new Slf4jLogger(props.getLoggerName()));
        builder.logLevel(props.getLoggerLevel());
    }

    private void initEncoder() {
        try {
            builder.encoder(applicationContext.getBean(Encoder.class));
        } catch (BeansException ignored) {
        }
    }

    private void initDecoder() {
        try {
            builder.decoder(applicationContext.getBean(Decoder.class));
        } catch (BeansException ignored) {
        }
    }

    private void initErrorDecoder() {
        try {
            builder.errorDecoder(applicationContext.getBean(ErrorDecoder.class));
        } catch (BeansException ignored) {
        }
    }

    private void initContract() {
        try {
            builder.contract(applicationContext.getBean(Contract.class));
        } catch (BeansException ignored) {
        }
    }

    private void initInterceptors() {
        try {
            builder.requestInterceptors(applicationContext.getBeansOfType(RequestInterceptor.class).values());
        } catch (BeansException ignored) {
        }
    }

    private void init404() {
        if (props.isDecode404()) {
            builder.decode404();
        }
    }

    private void initBasicAuth() {
        try {
            val basicAuth = props.getBasicAuth();
            val username = basicAuth.getUsername();
            val password = basicAuth.getPassword();
            val charset = basicAuth.getCharset();
            if (basicAuth.getUsername() != null && basicAuth.getPassword() != null) {
                val interceptor = new BasicAuthRequestInterceptor(username, password, charset);
                builder.requestInterceptor(interceptor);
            }
        } catch (NullPointerException ignored) {
        }
    }

    private void initBearerAuth() {
        try {
            val token = props.getBearerAuth().getToken();
            if (StringUtils.hasText(token)) {
                val interceptor = new RequestInterceptor() {
                    @Override
                    public void apply(RequestTemplate template) {
                        final String headerValue;

                        if (!token.startsWith("Bearer ")) {
                            headerValue = "Bearer " + token;
                        } else {
                            headerValue = token;
                        }

                        template.header(HttpHeaders.AUTHORIZATION, headerValue);
                    }
                };
                builder.requestInterceptor(interceptor);
            }
        } catch (NullPointerException ignored) {
        }
    }

    private void initRetryer() {
        try {
            builder.retryer(applicationContext.getBean(Retryer.class));
        } catch (BeansException ignored) {
        }
    }

    private void initCapabilities() {
        try {
            for (Capability c : applicationContext.getBeansOfType(Capability.class).values()) {
                builder.addCapability(c);
            }
        } catch (BeansException ignored) {
        }
    }

    private void initOptions() {
        try {
            builder.options(applicationContext.getBean(Request.Options.class));
        } catch (BeansException ignored) {
        }
    }

    private void reset() {
        try {
            List<FeignBuilderCustomizer> customizers = new LinkedList<>(applicationContext.getBeansOfType(FeignBuilderCustomizer.class).values());
            customizers.forEach(it -> it.customize(clientType, builder));
        } catch (BeansException ignored) {
        }
    }

    private UrlSupplier getUrlSupplier(Class<?> urlSupplierType, String url) {

        // "url"配置的优先级高于"urlSupplier"

        if (!"".equals(url)) {
            return UrlSupplier.of(url);
        }

        if (urlSupplierType != void.class) {
            return (UrlSupplier) applicationContext.getBean(urlSupplierType); // 类型转换异常抛出就可以了
        }

        throw new IllegalArgumentException("url or urlSupplier not configured");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public void setClientType(Class<T> clientType) {
        this.clientType = clientType;
    }

    public void setUrlSupplierType(Class<?> urlSupplierType) {
        this.urlSupplierType = urlSupplierType;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
