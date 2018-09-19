/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.jsr310.support;

import com.github.yingzhuo.carnival.spring.SpringUtils;
import org.springframework.context.ApplicationContext;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;

/**
 * @author 应卓
 */
public abstract class ApplicationContextAwareConstraintValidator<A extends Annotation, T> implements ConstraintValidator<A, T> {

    @Override
    public final boolean isValid(T value, ConstraintValidatorContext context) {
        return isValid(SpringUtils.getApplicationContext(), value, context);
    }

    protected abstract boolean isValid(ApplicationContext applicationContext, T value, ConstraintValidatorContext context);

}
