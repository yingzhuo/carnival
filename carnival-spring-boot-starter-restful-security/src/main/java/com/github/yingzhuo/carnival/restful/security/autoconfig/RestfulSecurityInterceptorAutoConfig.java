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
import com.github.yingzhuo.carnival.restful.security.cache.CacheManager;
import com.github.yingzhuo.carnival.restful.security.core.RestfulSecurityInterceptor;
import com.github.yingzhuo.carnival.restful.security.mvc.UserDetailsPropertyHandlerMethodArgumentResolver;
import com.github.yingzhuo.carnival.restful.security.parser.AlwaysEmptyTokenParser;
import com.github.yingzhuo.carnival.restful.security.parser.TokenParser;
import com.github.yingzhuo.carnival.restful.security.realm.AlwaysEmptyUserDetailsRealm;
import com.github.yingzhuo.carnival.restful.security.realm.UserDetailsRealm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.core.OrderComparator;
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
    private List<TokenParser> tokenParsers;

    @Autowired
    private List<UserDetailsRealm> userDetailsRealms;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private TokenBlacklistManager tokenBlackListManager;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        TokenParser tp;
        UserDetailsRealm udr;

        if (tokenParsers.size() == 1) {
            tp = tokenParsers.get(0);
        } else {
            OrderComparator.sort(tokenParsers);
            tp = tokenParsers.stream().reduce(new AlwaysEmptyTokenParser(), TokenParser::or);
        }

        if (userDetailsRealms.size() == 1) {
            udr = userDetailsRealms.get(0);
        } else {
            OrderComparator.sort(userDetailsRealms);
            udr = userDetailsRealms.stream().reduce(new AlwaysEmptyUserDetailsRealm(), UserDetailsRealm::or);
        }

        final Integer interceptorOrder = AnnotationAttributesHolder.getValue(EnableRestfulSecurity.class, "interceptorOrder");
        final AuthenticationStrategy authenticationStrategy = AnnotationAttributesHolder.getValue(EnableRestfulSecurity.class, "authenticationStrategy");

        final RestfulSecurityInterceptor interceptor = new RestfulSecurityInterceptor();
        interceptor.setTokenBlacklistManager(tokenBlackListManager);
        interceptor.setTokenParser(tp);
        interceptor.setUserDetailsRealm(udr);
        interceptor.setCacheManager(cacheManager);
        interceptor.setAuthenticationStrategy(authenticationStrategy);
        registry.addInterceptor(interceptor).addPathPatterns("/", "/**").order(interceptorOrder);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new UserDetailsPropertyHandlerMethodArgumentResolver());
    }

}
