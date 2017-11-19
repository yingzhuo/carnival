/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.debug.support;

import com.github.yingzhuo.carnival.debug.DebugMessage;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

@Aspect
public class DebugMessageAdvice {

    private final boolean enabled;
    private final LoggerWrapper logger;

    public DebugMessageAdvice(boolean enabled, LogLevel logLevel, String loggerName) {
        this.enabled = enabled;
        this.logger = new LoggerWrapper(logLevel, loggerName);
    }

    @Around("@annotation(com.github.yingzhuo.carnival.debug.DebugMessage)")
    private Object around(ProceedingJoinPoint joinPoint) throws Throwable {

        if (!enabled) {
            return joinPoint.proceed();
        }

        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        DebugMessage annotation = method.getAnnotation(DebugMessage.class);
        Object[] args = joinPoint.getArgs();

        if (annotation == null) {
            return joinPoint.proceed();
        }

        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long end = System.currentTimeMillis();

        logger.doLog(StringUtils.repeat('-', 20));

        logger.doLog("[Method]: ");
        logger.doLog("\t\t\t{}", method);

        if (!"".equals(annotation.value())) {
            logger.doLog("[Message]: ");
            logger.doLog("\t\t\t{}", method);
        }

        logger.doLog("[Parameters]: ");
        if (args == null || args.length == 0) {
            logger.doLog("\t\t\t{}", "<no-args>");
        } else {
            logger.doLog("\t\t\t{}", joinPoint.getArgs());
        }

        logger.doLog("[Time-Consuming (ms)]: ");
        logger.doLog("\t\t\t{}", end - start);

        logger.doLog(StringUtils.repeat('-', 20));

        return result;
    }

}
