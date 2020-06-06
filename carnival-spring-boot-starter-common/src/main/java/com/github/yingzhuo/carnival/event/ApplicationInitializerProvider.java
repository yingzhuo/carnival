/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.event;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.util.LinkedList;
import java.util.List;

/**
 * @author 应卓
 * @since 1.6.15
 */
public class ApplicationInitializerProvider implements BeanPostProcessor {

    static final List<ApplicationInitializer> INITIALIZERS = new LinkedList<>();

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof ApplicationInitializer) {
            INITIALIZERS.add((ApplicationInitializer) bean);
        }
        return bean;
    }

}
