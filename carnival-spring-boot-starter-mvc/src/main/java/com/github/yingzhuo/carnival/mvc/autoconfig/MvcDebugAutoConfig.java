/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.mvc.autoconfig;

import com.github.yingzhuo.carnival.common.condition.ConditionalOnDebugMode;
import com.github.yingzhuo.carnival.mvc.support.DebugMvcInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 应卓
 */
@Slf4j
@ConditionalOnDebugMode
@ConditionalOnWebApplication
public class MvcDebugAutoConfig implements WebMvcConfigurer, InitializingBean {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new DebugMvcInterceptor()).addPathPatterns("/", "/**").order(Ordered.LOWEST_PRECEDENCE);
    }

    @Override
    public void afterPropertiesSet() {
        log.warn("Spring MVC debug-plugin enabled! DO NOT use this plugin in production environment.");
    }

}
