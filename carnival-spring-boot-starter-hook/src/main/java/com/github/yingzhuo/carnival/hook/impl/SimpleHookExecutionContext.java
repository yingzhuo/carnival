/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.hook.impl;

import com.github.yingzhuo.carnival.hook.HookExecutionContext;
import com.github.yingzhuo.carnival.hook.PostHookExecutionContext;
import com.github.yingzhuo.carnival.hook.PreHookExecutionContext;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;
import java.util.Optional;

/**
 * @author 应卓
 */
public class SimpleHookExecutionContext implements HookExecutionContext, PreHookExecutionContext, PostHookExecutionContext {

    private long timestamp;
    private Method method;
    private Object target;
    private Object[] args;
    private Throwable exception;
    private Object result;

    public SimpleHookExecutionContext(ProceedingJoinPoint call) {
        this(((MethodSignature)call.getSignature()).getMethod(), call.getTarget(), call.getArgs());
    }

    public SimpleHookExecutionContext(ProceedingJoinPoint call, Throwable exception, Object result) {
        this(call);
        this.exception = exception;
        this.result = result;
    }

    public SimpleHookExecutionContext(Method method, Object target, Object[] args) {
        this.timestamp = System.currentTimeMillis();
        this.method = method;
        this.target = target;
        this.args = args;
        this.exception = null;
        this.result = null;
    }

    public SimpleHookExecutionContext(Method method, Object target, Object[] args, Throwable exception, Object result) {
        this(method, target, args);
        this.exception = exception;
        this.result = result;
    }

    @Override
    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public Method getMethod() {
        return method;
    }

    @Override
    public Object getTarget() {
        return target;
    }

    @Override
    public Object[] getArgs() {
        return args;
    }

    @Override
    public Optional<Throwable> getException() {
        return Optional.ofNullable(exception);
    }

    @Override
    public Object getResult() {
        return result;
    }
}
