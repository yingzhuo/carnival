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
import com.github.yingzhuo.carnival.restful.security.core.RestfulSecurityInterceptor;
import com.github.yingzhuo.carnival.restful.security.mvc.UserDetailsPropertyHandlerMethodArgumentResolver;
import com.github.yingzhuo.carnival.restful.security.parser.CompositeTokenParser;
import com.github.yingzhuo.carnival.restful.security.parser.TokenParser;
import com.github.yingzhuo.carnival.restful.security.realm.CompositeUserDetailsRealm;
import com.github.yingzhuo.carnival.restful.security.realm.UserDetailsRealm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.core.OrderComparator;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.LinkedList;
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
    private TokenBlacklistManager tokenBlackListManager;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        final TokenParser tokenParser = getFinalTokenParser();
        final UserDetailsRealm userDetailsRealm = getFinalUserDetailsRealm();

        Integer interceptorOrder = AnnotationAttributesHolder.getValue(EnableRestfulSecurity.class, "interceptorOrder");
        if (interceptorOrder == null) {
            interceptorOrder = 0;
        }

        final AuthenticationStrategy authenticationStrategy = AnnotationAttributesHolder.getValue(EnableRestfulSecurity.class, "authenticationStrategy");

        final RestfulSecurityInterceptor interceptor = new RestfulSecurityInterceptor();
        interceptor.setTokenParser(tokenParser);
        interceptor.setUserDetailsRealm(userDetailsRealm);
        interceptor.setTokenBlacklistManager(tokenBlackListManager);
        interceptor.setAuthenticationStrategy(authenticationStrategy);
        registry.addInterceptor(interceptor).addPathPatterns("/", "/**").order(interceptorOrder);
    }

    private TokenParser getFinalTokenParser() {
        if (this.tokenParsers.size() == 1) {
            return tokenParsers.get(0);
        } else {
            List<TokenParser> list = new LinkedList<>(tokenParsers);
            OrderComparator.sort(list);
            return new CompositeTokenParser(list.toArray(new TokenParser[0]));
        }
    }

    private UserDetailsRealm getFinalUserDetailsRealm() {
        if (this.userDetailsRealms.size() == 1) {
            return userDetailsRealms.get(0);
        } else {
            List<UserDetailsRealm> list = new LinkedList<>(userDetailsRealms);
            OrderComparator.sort(list);
            return new CompositeUserDetailsRealm(list.toArray(new UserDetailsRealm[0]));
        }
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new UserDetailsPropertyHandlerMethodArgumentResolver());
    }

}
