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

import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Spring通用工具
 *
 * @author 应卓
 * @since 0.0.1
 */
public final class SpringUtils {

    static ApplicationContext AC = null;
    static Environment ENV = null;

    private SpringUtils() {
    }

    public static ApplicationContext getApplicationContext() {
        return AC;
    }

    public static Environment getEnvironment() {
        return ENV;
    }

    public static <B> B getBean(Class<B> beanType) {
        return getApplicationContext().getBean(beanType);
    }

    @SuppressWarnings("unchecked")
    public static <B> B getBean(String beanName) {
        return (B) getApplicationContext().getBean(beanName);
    }

    public static <B> B getBean(String beanName, Class<B> beanType) {
        return getApplicationContext().getBean(beanName, beanType);
    }

    public static Set<String> getActiveProfiles() {
        return Collections.unmodifiableSet(Arrays.stream(ENV.getActiveProfiles()).collect(Collectors.toSet()));
    }

    public static Set<String> getDefaultProfiles() {
        return Collections.unmodifiableSet(Arrays.stream(ENV.getDefaultProfiles()).collect(Collectors.toSet()));
    }

}
