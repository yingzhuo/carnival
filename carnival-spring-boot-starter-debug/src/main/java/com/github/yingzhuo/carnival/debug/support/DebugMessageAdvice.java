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

import com.github.yingzhuo.carnival.aop.AbstractAroundAdvice;
import com.github.yingzhuo.carnival.debug.DebugMessage;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

@Aspect
public class DebugMessageAdvice extends AbstractAroundAdvice {

    private final boolean enabled;
    private final LoggerWrapper logger;

    public DebugMessageAdvice(boolean enabled, LogLevel logLevel, String loggerName) {
        this.enabled = enabled;
        this.logger = new LoggerWrapper(logLevel, loggerName);
    }

    @Around("@annotation(com.github.yingzhuo.carnival.debug.DebugMessage)")
    private Object around(ProceedingJoinPoint call) throws Throwable {

        if (!enabled) {
            return call.proceed();
        }

        Method method = ((MethodSignature) call.getSignature()).getMethod();
        DebugMessage annotation = getMethodAnnotation(call, DebugMessage.class);
        Object[] args = call.getArgs();

        if (annotation == null) {
            return call.proceed();
        }

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
            logger.doLog("\t\t\t{}", call.getArgs());
        }

        logger.doLog(StringUtils.repeat('-', 20));

        return call.proceed();
    }

}
