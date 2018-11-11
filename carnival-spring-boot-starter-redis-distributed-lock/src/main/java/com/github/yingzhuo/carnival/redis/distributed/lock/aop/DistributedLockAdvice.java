/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.redis.distributed.lock.aop;

import com.github.yingzhuo.carnival.redis.distributed.lock.annotation.DistributedLock;
import com.github.yingzhuo.carnival.redis.distributed.lock.exception.CannotGetLockException;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;

/**
 * @author 应卓
 */
@Slf4j
@Aspect
public class DistributedLockAdvice implements Ordered {

    @Pointcut("@annotation(com.github.yingzhuo.carnival.redis.distributed.lock.annotation.DistributedLock)")
    public void pc() {
    }

    @Around("pc()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

        val method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        val annotation = method.getAnnotation(DistributedLock.class);

        if (annotation == null) {
            return joinPoint.proceed();
        }

        val args = joinPoint.getArgs();

        val keyBuilder = new StringBuilder();
        keyBuilder.append(method.toString().replaceAll(" ", "_"));
        keyBuilder.append("_");

        if (annotation.keyIndices().length != 0) {
            for (int i : annotation.keyIndices()) {
                val parameter = args[i];
                keyBuilder.append(parameter);
            }
        } else {
            for (Object arg : args) {
                keyBuilder.append(arg);
            }
        }

        val key = keyBuilder.toString();

        log.debug("key = {}", key);

        val lock = com.github.yingzhuo.carnival.redis.distributed.lock.DistributedLock.lock(key, annotation.expireInMillis());

        if (!lock) {
            throw new CannotGetLockException(getMessage(annotation.errorMessage()));
        }

        try {
            return joinPoint.getArgs();
        } finally {
            com.github.yingzhuo.carnival.redis.distributed.lock.DistributedLock.release(key);
        }
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }

    private String getMessage(String msg) {
        if (msg == null || ":::<NO MESSAGE>:::".equals(msg)) {
            return null;
        } else {
            return msg;
        }
    }
}
