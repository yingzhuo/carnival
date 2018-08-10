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
import com.github.yingzhuo.carnival.restful.security.EnableRestfulSecurity;
import com.github.yingzhuo.carnival.restful.security.cache.CacheManager;
import com.github.yingzhuo.carnival.restful.security.core.RestfulSecurityInterceptor;
import com.github.yingzhuo.carnival.restful.security.listener.AuthenticationListener;
import com.github.yingzhuo.carnival.restful.security.mvc.RestfulSecurityHandlerMethodArgumentResolver;
import com.github.yingzhuo.carnival.restful.security.parser.TokenParser;
import com.github.yingzhuo.carnival.restful.security.realm.UserDetailsRealm;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.core.OrderComparator;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
import java.util.Optional;

/**
 * @author 应卓
 */
@ConditionalOnWebApplication
@AutoConfigureAfter(RestfulSecurityAutoConfig.class)
public class RestfulSecurityInterceptorAutoConfig implements WebMvcConfigurer {

    @Autowired
    private List<TokenParser> tokenParserList;

    @Autowired
    private List<UserDetailsRealm> userDetailsRealmList;

    @Autowired
    private AuthenticationListener authenticationListener;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private Optional<LocaleResolver> localeResolverOption;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        val interceptor = new RestfulSecurityInterceptor();
        val interceptorOrder = EnableRestfulSecurity.ImportSelector.getConfig("interceptorOrder", Integer.class);
        val authenticationStrategy = EnableRestfulSecurity.ImportSelector.getConfig("authenticationStrategy", AuthenticationStrategy.class);

        localeResolverOption.ifPresent(interceptor::setLocaleResolver);
        interceptor.setTokenParser(getTokenParser());
        interceptor.setUserDetailsRealm(getUserDetailsRealm());
        interceptor.setAuthenticationListener(authenticationListener);
        interceptor.setCacheManager(cacheManager);
        interceptor.setAuthenticationStrategy(authenticationStrategy);
        registry.addInterceptor(interceptor).addPathPatterns("/", "/**").order(interceptorOrder);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new RestfulSecurityHandlerMethodArgumentResolver());
    }

    private TokenParser getTokenParser() {

        if (tokenParserList.size() == 1) {
            return tokenParserList.get(0);
        }

        OrderComparator.sort(tokenParserList);
        return tokenParserList.stream().reduce((webRequest, locale) -> Optional.empty(), TokenParser::chain);
    }

    private UserDetailsRealm getUserDetailsRealm() {

        if (userDetailsRealmList.size() == 1) {
            return userDetailsRealmList.get(0);
        }

        OrderComparator.sort(userDetailsRealmList);
        return userDetailsRealmList.stream().reduce(token -> Optional.empty(), UserDetailsRealm::chain);
    }

}
