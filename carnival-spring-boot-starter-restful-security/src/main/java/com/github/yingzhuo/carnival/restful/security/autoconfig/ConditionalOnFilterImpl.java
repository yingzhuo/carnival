/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.security.autoconfig;

import com.github.yingzhuo.carnival.restful.security.Implementation;
import com.github.yingzhuo.carnival.restful.security.RestfulSecurityConfigurer;
import org.springframework.beans.BeansException;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.lang.annotation.*;

/**
 * @author 应卓
 * @since 1.6.22
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Conditional(ConditionalOnFilterImpl.OnFilterImpl.class)
@interface ConditionalOnFilterImpl {

    class OnFilterImpl implements Condition {
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            try {
                return context.getBeanFactory().getBean(RestfulSecurityConfigurer.class).getImplementation() == Implementation.SERVLET_FILTER;
            } catch (BeansException | NullPointerException e) {
                return false;
            }
        }
    }

}
