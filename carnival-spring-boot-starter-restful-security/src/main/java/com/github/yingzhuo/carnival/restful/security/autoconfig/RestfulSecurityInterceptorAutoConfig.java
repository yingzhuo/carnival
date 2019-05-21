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
import com.github.yingzhuo.carnival.restful.security.chain.ChainNode;
import com.github.yingzhuo.carnival.restful.security.core.RestfulSecurityChainInterceptor;
import com.github.yingzhuo.carnival.restful.security.core.RestfulSecurityInterceptor;
import com.github.yingzhuo.carnival.restful.security.mvc.RestfulSecurityHandlerMethodArgumentResolver;
import com.github.yingzhuo.carnival.restful.security.mvc.UserDetailsPropertyHandlerMethodArgumentResolver;
import com.github.yingzhuo.carnival.restful.security.parser.TokenParser;
import com.github.yingzhuo.carnival.restful.security.realm.UserDetailsRealm;
import com.github.yingzhuo.carnival.restful.security.voter.UserDetailsVoter;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.core.OrderComparator;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
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
    private CacheManager cacheManager;

    @Autowired
    private Optional<LocaleResolver> localeResolverOption;

    @Autowired
    private TokenBlackList tokenBlackList;

    @Autowired
    private UserDetailsVoter userDetailsVoter;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        final Integer interceptorOrder = AnnotationAttributesHolder.getValue(EnableRestfulSecurity.class, "interceptorOrder");
        final AuthenticationStrategy authenticationStrategy = AnnotationAttributesHolder.getValue(EnableRestfulSecurity.class, "authenticationStrategy");

        if (userDetailsRealmList.size() > 1 || tokenParserList.size() > 1) {

            if (userDetailsRealmList.size() != tokenParserList.size()) {
                val msg = String.format("Number of TokenParsers is %d, but number of UserDetailsRealms is %d.", tokenParserList.size(), userDetailsRealmList.size());
                throw new IllegalStateException(msg);
            }

            val interceptor = new RestfulSecurityChainInterceptor();
            localeResolverOption.ifPresent(interceptor::setLocaleResolver);
            interceptor.setTokenBlackList(tokenBlackList);
            interceptor.setUserDetailsVoter(userDetailsVoter);
            interceptor.setCacheManager(cacheManager);
            interceptor.setChainNodes(getChainNodes());
            interceptor.setAuthenticationStrategy(authenticationStrategy);
            registry.addInterceptor(interceptor).addPathPatterns("/", "/**").order(interceptorOrder);

        } else {
            val interceptor = new RestfulSecurityInterceptor();
            localeResolverOption.ifPresent(interceptor::setLocaleResolver);
            interceptor.setTokenBlackList(tokenBlackList);
            interceptor.setTokenParser(tokenParserList.get(0));
            interceptor.setUserDetailsRealm(userDetailsRealmList.get(0));
            interceptor.setCacheManager(cacheManager);
            interceptor.setAuthenticationStrategy(authenticationStrategy);
            registry.addInterceptor(interceptor).addPathPatterns("/", "/**").order(interceptorOrder);
        }
    }

    private List<ChainNode> getChainNodes() {
        this.userDetailsRealmList.sort(OrderComparator.INSTANCE);
        this.tokenParserList.sort(OrderComparator.INSTANCE);

        val list = new ArrayList<ChainNode>();
        for (int i = 0; i < tokenParserList.size(); i++) {
            list.add(ChainNode.of(tokenParserList.get(i), userDetailsRealmList.get(i)));
        }

        return list;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new RestfulSecurityHandlerMethodArgumentResolver());
        argumentResolvers.add(new UserDetailsPropertyHandlerMethodArgumentResolver());
    }

}
