/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.datasource.impl;

import com.github.yingzhuo.carnival.datasource.DataSourceSwitch;
import com.github.yingzhuo.carnival.datasource.DataSourceWrapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.Ordered;

import javax.sql.DataSource;
import java.lang.reflect.Method;

@Aspect
public class DataSourceWrapperAopAdvice implements ApplicationContextAware, Ordered {

    private ApplicationContext applicationContext;
    private int order = 0;

    public DataSourceWrapperAopAdvice() {
        super();
    }

    @Override
    public int getOrder() {
        return this.order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Around("@annotation(com.lincomb.origen.datasource.DataSourceSwitch)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

        if (!skip()) {
            String dataSourceName = getDataSourceName(joinPoint);
            DataSourceWrapper.Switch.setName(dataSourceName);
        }

        return joinPoint.proceed();
    }

    private boolean skip() {
        try {
            DataSource dataSource = applicationContext.getBean(DataSource.class);
            return !(dataSource instanceof DataSourceWrapper);
        } catch (NoSuchBeanDefinitionException e) {
            return true;
        }
    }

    private String getDataSourceName(ProceedingJoinPoint joinPoint) {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        DataSourceSwitch annotation = getMethodAnnotation(joinPoint);

        if (annotation == null) {
            return null;
        } else {
            String name = annotation.value();
            return "".equals(name) ? null : name;
        }
    }

    private DataSourceSwitch getMethodAnnotation(ProceedingJoinPoint joinPoint) {
        try {
            Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
            DataSourceSwitch annotation = method.getAnnotation(DataSourceSwitch.class);

            if (annotation == null) {
                Class<?> targetClass = joinPoint.getTarget().getClass();
                method = targetClass.getMethod(method.getName(), method.getParameterTypes());

                if (method != null) {
                    annotation = method.getAnnotation(DataSourceSwitch.class);
                }
            }

            return annotation;
        } catch (Exception e) {
            return null;
        }
    }
}
