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

import com.github.yingzhuo.carnival.common.autoconfig.support.AnnotationAttributesHolder;
import com.github.yingzhuo.carnival.restful.security.AuthenticationStrategy;
import com.github.yingzhuo.carnival.restful.security.EnableRestfulSecurity;
import com.github.yingzhuo.carnival.restful.security.blacklist.TokenBlackList;
import com.github.yingzhuo.carnival.restful.security.cache.CacheManager;
import com.github.yingzhuo.carnival.restful.security.core.RestfulSecurityInterceptor;
import com.github.yingzhuo.carnival.restful.security.mvc.UserDetailsPropertyHandlerMethodArgumentResolver;
import com.github.yingzhuo.carnival.restful.security.parser.TokenParser;
import com.github.yingzhuo.carnival.restful.security.realm.UserDetailsRealm;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author 应卓
 */
@ConditionalOnWebApplication
@AutoConfigureAfter(RestfulSecurityAutoConfig.class)
public class RestfulSecurityInterceptorAutoConfig implements WebMvcConfigurer {

    @Autowired
    private TokenParser tokenParser;

    @Autowired
    private UserDetailsRealm userDetailsRealm;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private TokenBlackList tokenBlackList;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        final Integer interceptorOrder = AnnotationAttributesHolder.getValue(EnableRestfulSecurity.class, "interceptorOrder");
        final AuthenticationStrategy authenticationStrategy = AnnotationAttributesHolder.getValue(EnableRestfulSecurity.class, "authenticationStrategy");

        val interceptor = new RestfulSecurityInterceptor();
        interceptor.setTokenBlackList(tokenBlackList);
        interceptor.setTokenParser(tokenParser);
        interceptor.setUserDetailsRealm(userDetailsRealm);
        interceptor.setCacheManager(cacheManager);
        interceptor.setAuthenticationStrategy(authenticationStrategy);
        registry.addInterceptor(interceptor).addPathPatterns("/", "/**").order(interceptorOrder);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new UserDetailsPropertyHandlerMethodArgumentResolver());
    }

}
