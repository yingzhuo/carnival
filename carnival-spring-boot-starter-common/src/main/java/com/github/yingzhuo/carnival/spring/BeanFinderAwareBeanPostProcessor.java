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
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * @author 应卓
 * @see BeanFinder
 * @see BeanFinderAware
 * @since 1.10.19
 */
public class BeanFinderAwareBeanPostProcessor implements BeanPostProcessor {

    private final BeanFinder beanFinder;

    public BeanFinderAwareBeanPostProcessor(BeanFinder beanFinder) {
        this.beanFinder = beanFinder;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof BeanFinderAware) {
            ((BeanFinderAware) bean).setBeanFinder(beanFinder);
        }
        return bean;
    }

}
