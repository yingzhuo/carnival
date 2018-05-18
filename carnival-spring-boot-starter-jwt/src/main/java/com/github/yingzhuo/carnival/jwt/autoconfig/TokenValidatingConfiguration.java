/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.jwt.autoconfig;

import com.github.yingzhuo.carnival.jwt.props.TokenValidatingProps;
import com.github.yingzhuo.carnival.jwt.validating.AuthorizationManager;
import com.github.yingzhuo.carnival.jwt.validating.TokenParser;
import com.github.yingzhuo.carnival.jwt.validating.impl.DefaultTokenParser;
import com.github.yingzhuo.carnival.jwt.validating.mvc.JwtContextHandlerMethodArgumentResolver;
import com.github.yingzhuo.carnival.jwt.validating.mvc.TokenValidatingInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author 应卓
 */
@EnableConfigurationProperties(TokenValidatingProps.class)
@ConditionalOnWebApplication
@ConditionalOnProperty(prefix = "carnival.jwt.token-validating", name = "enabled", havingValue = "true", matchIfMissing = true)
public class TokenValidatingConfiguration implements WebMvcConfigurer {

    @Autowired
    private TokenValidatingProps props;

    @Autowired
    private TokenParser tokenParser;

    @Autowired
    private AuthorizationManager authorizationManager;

    @Bean
    @ConditionalOnMissingBean
    public TokenParser tokenParser() {
        return new DefaultTokenParser();
    }

    @Bean
    @ConditionalOnMissingBean
    public AuthorizationManager authorizationManager() {
        return AuthorizationManager.NOP;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        TokenValidatingInterceptor interceptor = new TokenValidatingInterceptor();
        interceptor.setAuthorizationManager(authorizationManager);
        interceptor.setSecret(props.getSecret());
        interceptor.setSignatureAlgorithm(props.getSignatureAlgorithm());
        interceptor.setTokenParser(tokenParser);
        interceptor.setExcludePatterns(props.getExcludePatterns());
        registry.addInterceptor(interceptor).addPathPatterns("/", "/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new JwtContextHandlerMethodArgumentResolver());
    }

}
