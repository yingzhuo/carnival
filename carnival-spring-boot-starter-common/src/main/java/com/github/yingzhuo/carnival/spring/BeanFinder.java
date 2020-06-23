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

import java.util.*;

/**
 * @author 应卓
 * @since 1.6.21
 */
public final class BeanFinder {

    private final ApplicationContext context;

    public BeanFinder() {
        this.context = Objects.requireNonNull(SpringUtils.getApplicationContext());
    }

    public BeanFinder(ApplicationContext applicationContext) {
        this.context = Objects.requireNonNull(applicationContext);
    }

    public static BeanFinder newInstance() {
        return new BeanFinder();
    }

    public static BeanFinder newInstance(ApplicationContext context) {
        return new BeanFinder(context);
    }

    public <T> T getOneOrPrimary(Class<T> beanType) {
        try {
            return context.getBean(beanType);
        } catch (BeansException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public <T> Optional<T> getOneOrPrimaryQuietly(Class<T> beanType) {
        try {
            return Optional.of(getOneOrPrimary(beanType));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public <T> List<T> getMultiple(Class<T> beanType) {
        try {
            final List<T> beans = new ArrayList<>(context.getBeansOfType(beanType).values());
            OrderComparator.sort(beans);
            return Collections.unmodifiableList(beans);
        } catch (BeansException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public <T> Optional<List<T>> getMultipleQuietly(Class<T> beanType) {
        try {
            List<T> beans = getMultiple(beanType);
            return beans.isEmpty() ? Optional.empty() : Optional.of(beans);
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

}
