/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.core.OrderComparator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author 应卓
 * @see ApplicationContext
 * @since 1.6.21
 */
public final class BeanFinder {

    private final ApplicationContext context;

    public BeanFinder(ApplicationContext applicationContext) {
        this.context = Objects.requireNonNull(applicationContext);
    }

    public static BeanFinder newInstance(ApplicationContext context) {
        return new BeanFinder(context);
    }

    public <T> T getPrimary(Class<T> beanType) {
        try {
            return context.getBean(beanType);
        } catch (BeansException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public <T> T getPrimary(Class<T> beanType, String beanName) {
        try {
            return context.getBean(beanName, beanType);
        } catch (BeansException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public <T> Optional<T> getPrimaryQuietly(Class<T> beanType) {
        try {
            return Optional.of(getPrimary(beanType));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public <T> Optional<T> getPrimaryQuietly(Class<T> beanType, String beanName) {
        try {
            return Optional.of(getPrimary(beanType, beanName));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public <T> List<T> getMultiple(Class<T> beanType) {
        try {
            final List<T> beans = new ArrayList<>(context.getBeansOfType(beanType).values());
            OrderComparator.sort(beans);
            return beans;
        } catch (BeansException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public <T> List<T> getMultipleQuietly(Class<T> beanType) {
        try {
            return getMultiple(beanType);
        } catch (IllegalArgumentException e) {
            return new ArrayList<>();
        }
    }

}
