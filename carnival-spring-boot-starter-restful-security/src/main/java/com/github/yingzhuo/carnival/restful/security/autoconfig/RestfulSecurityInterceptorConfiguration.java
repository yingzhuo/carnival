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

import com.github.yingzhuo.carnival.restful.security.ImportSelectorConfigHolder;
import com.github.yingzhuo.carnival.restful.security.cache.CacheManager;
import com.github.yingzhuo.carnival.restful.security.core.RestfulSecurityInterceptor;
import com.github.yingzhuo.carnival.restful.security.listener.AuthenticationListener;
import com.github.yingzhuo.carnival.restful.security.mvc.RestfulSecurityHandlerMethodArgumentResolver;
import com.github.yingzhuo.carnival.restful.security.parser.TokenParser;
import com.github.yingzhuo.carnival.restful.security.realm.UserDetailsRealm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
import java.util.Optional;

@ConditionalOnWebApplication
@AutoConfigureAfter(RestfulSecurityBeanConfiguration.class)
public class RestfulSecurityInterceptorConfiguration implements WebMvcConfigurer {

    @Autowired private TokenParser tokenParser;
    @Autowired private UserDetailsRealm userDetailsRealm;
    @Autowired private AuthenticationListener authenticationListener;
    @Autowired private CacheManager cacheManager;
    @Autowired(required = false) private LocaleResolver localeResolver;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        RestfulSecurityInterceptor interceptor = new RestfulSecurityInterceptor();
        Optional.ofNullable(localeResolver).ifPresent(interceptor::setLocaleResolver);
        interceptor.setTokenParser(tokenParser);
        interceptor.setUserDetailsRealm(userDetailsRealm);
        interceptor.setAuthenticationListener(authenticationListener);
        interceptor.setCacheManager(cacheManager);
        registry.addInterceptor(interceptor).addPathPatterns("/", "/**").order(ImportSelectorConfigHolder.interceptorOrder);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new RestfulSecurityHandlerMethodArgumentResolver());
    }

}
