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
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.ApplicationArguments;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.core.OrderComparator;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Spring通用工具
 *
 * @author 应卓
 */
@SuppressWarnings("unchecked")
public final class SpringUtils {

    static ApplicationContext AC = null;
    static Environment ENV = null;
    static ApplicationArguments APP_ARGS = null;

    private SpringUtils() {
    }

    /* -------------------------------------------------------------------------------------------------------------- */

    public static ApplicationContext getApplicationContext() {
        return AC;
    }

    public static Environment getEnvironment() {
        return ENV;
    }

    public static ResourceLoader getResourceLoader() {
        return AC;
    }

    public static ResourcePatternResolver getResourcePatternResolver() {
        return AC;
    }

    public static MessageSource getMessageSource() {
        return AC;
    }

    public static ApplicationEventPublisher getApplicationEventPublisher() {
        return AC;
    }

    /* -------------------------------------------------------------------------------------------------------------- */

    public static String getSpringId() {
        return ENV.getProperty("spring.id");
    }

    /* -------------------------------------------------------------------------------------------------------------- */

    public static <B> B getBean(Class<B> beanType) {
        return getApplicationContext().getBean(beanType);
    }

    public static <B> B getBean(Class<B> beanType, B defaultIfNotFound) {
        try {
            return getBean(beanType);
        } catch (NoSuchBeanDefinitionException e) {
            return defaultIfNotFound;
        }
    }

    public static <B> B getBean(String beanName) {
        return (B) getApplicationContext().getBean(beanName);
    }

    public static <B> B getBean(String beanName, B defaultIfNotFound) {
        try {
            return getBean(beanName);
        } catch (NoSuchBeanDefinitionException e) {
            return defaultIfNotFound;
        }
    }

    public static <B> B getBean(String beanName, Class<B> beanType) {
        return getApplicationContext().getBean(beanName, beanType);
    }

    public static <B> B getBean(String beanName, Class<B> beanType, B defaultIfNotFound) {
        try {
            return getBean(beanName, beanType);
        } catch (NoSuchBeanDefinitionException e) {
            return defaultIfNotFound;
        }
    }

    public static <B> B getBean(Class<B> beanType, Object... constructionArgs) {
        return getObjectProvider(beanType).getObject(constructionArgs);
    }

    public static <B> ObjectProvider<B> getObjectProvider(Class<B> beanType) {
        return getApplicationContext().getBeanProvider(beanType);
    }

    public static <B> List<B> getBeanList(Class<B> beanType) {
        try {
            return Collections.unmodifiableList(new ArrayList<>(AC.getBeansOfType(beanType).values()));
        } catch (BeansException e) {
            return Collections.emptyList();
        }
    }

    public static <B> List<B> getBeanListAndSort(Class<B> beanType) {
        try {
            final List<B> list = new ArrayList<>(AC.getBeansOfType(beanType).values());
            OrderComparator.sort(list);
            return Collections.unmodifiableList(list);
        } catch (BeansException e) {
            return Collections.emptyList();
        }
    }

    /* -------------------------------------------------------------------------------------------------------------- */

    public static boolean containsBean(Class<?> beanType) {
        try {
            AC.getBean(beanType);
            return true;
        } catch (NoUniqueBeanDefinitionException e) {
            return true;
        } catch (NoSuchBeanDefinitionException e) {
            return false;
        }
    }

}
