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
import com.github.yingzhuo.carnival.restful.security.RestfulSecurityConfigurer;
import com.github.yingzhuo.carnival.restful.security.blacklist.TokenBlacklistManager;
import com.github.yingzhuo.carnival.restful.security.core.ReflectCache;
import com.github.yingzhuo.carnival.restful.security.core.RestfulSecurityInterceptor;
import com.github.yingzhuo.carnival.restful.security.mvc.RestfulSecurityHandlerMethodArgumentResolver;
import com.github.yingzhuo.carnival.restful.security.parser.TokenParser;
import com.github.yingzhuo.carnival.restful.security.realm.UserDetailsRealm;
import com.github.yingzhuo.carnival.restful.security.realm.x.ExtraUserDetailsRealm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.Ordered;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author 应卓
 */
@Lazy(false)
@ConditionalOnWebApplication
@AutoConfigureAfter(RestfulSecurityBeanAutoConfig.class)
public class RestfulSecurityCoreAutoConfig implements WebMvcConfigurer, ApplicationRunner {

    @Autowired(required = false)
    private RestfulSecurityConfigurer configurer;

    @Autowired(required = false)
    private TokenParser injectedTokenParser;

    @Autowired(required = false)
    private UserDetailsRealm injectedUserDetailsRealm;

    @Autowired(required = false)
    private TokenBlacklistManager injectedTokenBlackListManager;

    @Autowired(required = false)
    private ExtraUserDetailsRealm injectedExtraUserDetailsRealm;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        final RestfulSecurityInterceptor interceptor = new RestfulSecurityInterceptor();
        interceptor.setAuthenticationStrategy(getAuthenticationStrategy());
        interceptor.setTokenParser(getTokenParser());
        interceptor.setUserDetailsRealm(getUserDetailsRealm());
        interceptor.setTokenBlacklistManager(getTokenBlackListManager());
        interceptor.setExtraUserDetailsRealm(getExtraUserDetailsRealm());
        registry.addInterceptor(interceptor).addPathPatterns("/", "/**").order(Ordered.HIGHEST_PRECEDENCE);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new RestfulSecurityHandlerMethodArgumentResolver());
    }

    @Override
    public void run(ApplicationArguments args) {
        ReflectCache.init();
    }

    private AuthenticationStrategy getAuthenticationStrategy() {
        AuthenticationStrategy strategy = null;
        if (configurer != null) {
            strategy = configurer.getAuthenticationStrategy();
        }

        if (strategy == null) {
            strategy = AuthenticationStrategy.ANNOTATED_REQUESTS;
        }
        return strategy;
    }

    private TokenParser getTokenParser() {
        if (injectedTokenParser != null) {
            return injectedTokenParser;
        }

        TokenParser tokenParser = configurer != null ? configurer.getTokenParser() : null;

        if (tokenParser == null) {
            throw new NullPointerException("TokenParser NOT configured.");
        }

        return tokenParser;
    }

    private UserDetailsRealm getUserDetailsRealm() {
        if (injectedUserDetailsRealm != null) {
            return injectedUserDetailsRealm;
        }

        UserDetailsRealm userDetailsRealm = configurer != null ? configurer.getUserDetailsRealm() : null;

        if (userDetailsRealm == null) {
            throw new NullPointerException("UserDetailsRealm NOT configured.");
        }

        return userDetailsRealm;
    }

    private TokenBlacklistManager getTokenBlackListManager() {
        if (injectedTokenBlackListManager != null) {
            return injectedTokenBlackListManager;
        }
        return configurer != null ? configurer.getTokenBlacklistManager() : null;
    }

    private ExtraUserDetailsRealm getExtraUserDetailsRealm() {
        if (injectedExtraUserDetailsRealm != null) {
            return injectedExtraUserDetailsRealm;
        }
        return configurer != null ? configurer.getExtraUserDetailsRealm() : null;
    }
}
