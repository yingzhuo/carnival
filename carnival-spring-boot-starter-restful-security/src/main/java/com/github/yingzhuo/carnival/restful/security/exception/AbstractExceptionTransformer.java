/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.security.exception;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author 应卓
 * @since 1.6.5
 */
public abstract class AbstractExceptionTransformer implements ExceptionTransformer {

    private final Set<Class<?>> set;

    public AbstractExceptionTransformer(Class<?>... exceptionTypes) {
        this.set = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(exceptionTypes)));
    }

    @Override
    public boolean isSupportsType(Class<?> exceptionType) {
        return set.contains(exceptionType);
    }

    @Override
    public abstract Exception transform(Exception from);

}
