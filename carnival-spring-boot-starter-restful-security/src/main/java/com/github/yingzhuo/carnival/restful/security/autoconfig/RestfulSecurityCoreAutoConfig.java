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

import com.github.yingzhuo.carnival.restful.security.AuthenticationStrategy;
import com.github.yingzhuo.carnival.restful.security.Implementation;
import com.github.yingzhuo.carnival.restful.security.RestfulSecurityConfigurer;
import com.github.yingzhuo.carnival.restful.security.blacklist.TokenBlacklistManager;
import com.github.yingzhuo.carnival.restful.security.core.ReflectCache;
import com.github.yingzhuo.carnival.restful.security.core.RestfulSecurityFilter;
import com.github.yingzhuo.carnival.restful.security.core.RestfulSecurityInterceptor;
import com.github.yingzhuo.carnival.restful.security.exceptionhandler.ExceptionHandler;
import com.github.yingzhuo.carnival.restful.security.mvc.RestfulSecurityHandlerMethodArgumentResolver;
import com.github.yingzhuo.carnival.restful.security.parser.TokenParser;
import com.github.yingzhuo.carnival.restful.security.realm.UserDetailsRealm;
import com.github.yingzhuo.carnival.restful.security.whitelist.TokenWhitelistManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author 应卓
 */
@ConditionalOnWebApplication
@AutoConfigureAfter(RestfulSecurityBeanAutoConfig.class)
public class RestfulSecurityCoreAutoConfig implements WebMvcConfigurer, ApplicationRunner {

    @Autowired(required = false)
    private RestfulSecurityConfigurer configurer = new RestfulSecurityConfigurer() {
    };

    @Autowired(required = false)
    private TokenParser injectedTokenParser;

    @Autowired(required = false)
    private UserDetailsRealm injectedUserDetailsRealm;

    @Autowired(required = false)
    private TokenBlacklistManager injectedTokenBlackListManager;

    @Autowired(required = false)
    private TokenWhitelistManager injectedTokenWhitelistManager;

    @Autowired(required = false)
    private ExceptionHandler injectedExceptionHandler;

    @Bean
    @ConditionalOnFilterImpl
    public FilterRegistrationBean<RestfulSecurityFilter> restfulSecurityFilter() {
        final FilterRegistrationBean<RestfulSecurityFilter> bean = new FilterRegistrationBean<>();
        final RestfulSecurityFilter filter = new RestfulSecurityFilter();
        filter.setAuthenticationStrategy(getAuthenticationStrategy());
        filter.setTokenParser(getTokenParser());
        filter.setUserDetailsRealm(getUserDetailsRealm());
        filter.setTokenBlacklistManager(getTokenBlackListManager());
        filter.setTokenWhitelistManager(getTokenWhitelistManager());
        filter.setExceptionHandler(exceptionHandler());
        bean.setFilter(filter);
        bean.setOrder(getOrder());
        bean.addUrlPatterns(getPathPatterns());
        bean.setName(RestfulSecurityFilter.class.getName());
        return bean;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        if (isInterceptorImpl()) {
            final RestfulSecurityInterceptor interceptor = new RestfulSecurityInterceptor();
            interceptor.setAuthenticationStrategy(getAuthenticationStrategy());
            interceptor.setTokenParser(getTokenParser());
            interceptor.setUserDetailsRealm(getUserDetailsRealm());
            interceptor.setTokenBlacklistManager(getTokenBlackListManager());
            interceptor.setTokenWhitelistManager(getTokenWhitelistManager());

            registry.addInterceptor(interceptor)
                    .addPathPatterns(getPathPatterns())
                    .order(getOrder());
        }

    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new RestfulSecurityHandlerMethodArgumentResolver());
    }

    @Override
    public void run(ApplicationArguments args) {
        ReflectCache.init();    // 初始化反射工具
    }

    private int getOrder() {
        return configurer.getOrder();
    }

    private AuthenticationStrategy getAuthenticationStrategy() {
        return configurer.getAuthenticationStrategy();
    }

    private TokenParser getTokenParser() {
        if (injectedTokenParser != null) {
            return injectedTokenParser;
        }

        TokenParser tokenParser = configurer.getTokenParser();
        if (tokenParser == null) {
            throw new NullPointerException("TokenParser NOT configured.");
        }

        return tokenParser;
    }

    private UserDetailsRealm getUserDetailsRealm() {
        if (injectedUserDetailsRealm != null) {
            return injectedUserDetailsRealm;
        }

        UserDetailsRealm userDetailsRealm = configurer.getUserDetailsRealm();

        if (userDetailsRealm == null) {
            throw new NullPointerException("UserDetailsRealm NOT configured.");
        }

        return userDetailsRealm;
    }

    private TokenBlacklistManager getTokenBlackListManager() {
        if (injectedTokenBlackListManager != null) {
            return injectedTokenBlackListManager;
        }
        return configurer.getTokenBlacklistManager();
    }

    private TokenWhitelistManager getTokenWhitelistManager() {
        if (injectedTokenWhitelistManager != null) {
            return injectedTokenWhitelistManager;
        }
        return configurer.getTokenWhitelistManager();
    }

    private ExceptionHandler exceptionHandler() {
        if (injectedExceptionHandler != null) {
            return injectedExceptionHandler;
        }

        ExceptionHandler exceptionHandler = configurer.getExceptionHandler();

        if (exceptionHandler == null) {
            throw new NullPointerException("ExceptionHandler NOT configured.");
        }

        return exceptionHandler;
    }

    private boolean isInterceptorImpl() {
        return configurer.getImplementation() == null ||
                configurer.getImplementation() == Implementation.SPRING_MAC_INTERCEPTOR;
    }

    private String[] getPathPatterns() {
        String[] patterns = configurer.getPathPatterns();
        if (patterns != null) return patterns;

        Implementation impl = configurer.getImplementation();
        if (impl == null) {
            impl = Implementation.SPRING_MAC_INTERCEPTOR;
        }

        if (impl == Implementation.SPRING_MAC_INTERCEPTOR) {
            return new String[]{"/**"};
        } else {
            return new String[]{"/*"};
        }
    }

}
