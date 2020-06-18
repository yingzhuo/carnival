/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.openfeign.resilience4j;

import java.lang.annotation.*;

/**
 * @author 应卓
 * @since 1.6.19
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CircuitBreaker {

    public int minimumNumberOfCalls() default 100;

    public int slidingWindowSize() default 100;

    public int permittedNumberOfCallsInHalfOpenState() default 10;

    public int failureRateThreshold() default 50;

    public int slowCallRateThreshold() default 100;

    public int slowCallDurationThresholdInSeconds() default 60;

    public Class<? extends Exception>[] exceptionTypes() default {};

    public Class<? extends Exception>[] ignoreExceptionTypes() default {};

}
