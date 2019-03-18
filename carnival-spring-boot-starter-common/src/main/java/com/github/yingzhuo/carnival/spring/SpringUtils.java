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

import lombok.val;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.context.ApplicationContext;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Spring通用工具
 *
 * @author 应卓
 */
public final class SpringUtils {

    static ApplicationContext AC = null;
    static Environment ENV = null;
    static ApplicationArguments APP_ARGS = null;
    static List<String> CMD_ARGS = null;

    private SpringUtils() {
        super();
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

    public static BeanFactory getBeanFactory() {
        return AC;
    }

    /* -------------------------------------------------------------------------------------------------------------- */

    public static String getSpringId() {
        return getBean("__spring_id__", String.class);
    }

    /* -------------------------------------------------------------------------------------------------------------- */

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

    /* -------------------------------------------------------------------------------------------------------------- */

    public static ConversionService getConversionService() {
        return getBean(ConversionService.class);
    }

    public static Locale getLocale() {
        val request = ServletUtils.getRequest();
        return RequestContextUtils.getLocale(request);
    }

    public static TimeZone getTimeZone() {
        val request = ServletUtils.getRequest();
        return RequestContextUtils.getTimeZone(request);
    }

}
