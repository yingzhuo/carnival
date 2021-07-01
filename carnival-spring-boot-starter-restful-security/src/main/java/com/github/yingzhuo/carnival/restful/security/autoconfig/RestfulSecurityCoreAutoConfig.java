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
import com.github.yingzhuo.carnival.restful.security.listener.Listener;
import com.github.yingzhuo.carnival.restful.security.mvc.*;
import com.github.yingzhuo.carnival.restful.security.parser.TokenParser;
import com.github.yingzhuo.carnival.restful.security.realm.UserDetailsRealm;
import com.github.yingzhuo.carnival.restful.security.whitelist.TokenWhitelistManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author 应卓
 */
@AutoConfigureAfter(RestfulSecurityBeanAutoConfig.class)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
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
    private Listener injectedListener;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        final RestfulSecurityInterceptor interceptor = new RestfulSecurityInterceptor();
        interceptor.setAuthenticationStrategy(getAuthenticationStrategy());
        interceptor.setTokenParser(getTokenParser());
        interceptor.setUserDetailsRealm(getUserDetailsRealm());
        interceptor.setTokenBlacklistManager(getTokenBlackListManager());
        interceptor.setTokenWhitelistManager(getTokenWhitelistManager());
        interceptor.setListener(getListener());

        registry.addInterceptor(interceptor)
                .addPathPatterns(getPathPatterns())
                .order(getOrder());
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new AnnotationIdSupport());
        resolvers.add(new AnnotationUsernameSupport());
        resolvers.add(new AnnotationStringTokenValueSupport());
        resolvers.add(new AnnotationNativeUserSupport());
        resolvers.add(new AnnotationSecurityContextSupport());

        resolvers.add(new TypeUserDetailsSupport());
        resolvers.add(new TypeTokenSupport());
        resolvers.add(new TypeStringTokenSupport());
        resolvers.add(new TypeJwtTokenSupport());
        resolvers.add(new TypeBytesTokenSupport());
        resolvers.add(new TypeUsernamePasswordTokenSupport());
    }

    @Override
    public void run(ApplicationArguments args) {
        ReflectCache.init();
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

    private String[] getPathPatterns() {
        String[] patterns = configurer.getPathPatterns();
        if (patterns == null || patterns.length == 0) {
            patterns = new String[]{"/**"};
        }
        return patterns;
    }

    private Listener getListener() {
        if (injectedListener != null) {
            return injectedListener;
        }
        return configurer.getListener();
    }

}
