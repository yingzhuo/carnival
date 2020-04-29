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

    @Autowired
    private TokenParser tokenParser;

    @Autowired
    private UserDetailsRealm userDetailsRealm;

    @Autowired(required = false)
    private TokenBlacklistManager tokenBlackListManager;

    @Autowired(required = false)
    private ExtraUserDetailsRealm extraUserDetailsRealm;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        Integer interceptorOrder = AnnotationAttributesHolder.getValue(EnableRestfulSecurity.class, "interceptorOrder");
        if (interceptorOrder == null) {
            interceptorOrder = 0;
        }

        final AuthenticationStrategy authenticationStrategy = AnnotationAttributesHolder.getValue(EnableRestfulSecurity.class, "authenticationStrategy");

        final RestfulSecurityInterceptor interceptor = new RestfulSecurityInterceptor();
        interceptor.setAuthenticationStrategy(authenticationStrategy);
        interceptor.setTokenParser(tokenParser);
        interceptor.setUserDetailsRealm(userDetailsRealm);
        interceptor.setTokenBlacklistManager(tokenBlackListManager);
        interceptor.setExtraUserDetailsRealm(extraUserDetailsRealm);
        registry.addInterceptor(interceptor).addPathPatterns("/", "/**").order(interceptorOrder);
    }


    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new RestfulSecurityHandlerMethodArgumentResolver());
    }

    @Override
    public void run(ApplicationArguments args) {
        ReflectCache.init();
    }

}
