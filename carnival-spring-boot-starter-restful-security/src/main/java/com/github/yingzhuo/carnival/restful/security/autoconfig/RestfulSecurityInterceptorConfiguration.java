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

import com.github.yingzhuo.carnival.restful.security.AuthenticationListener;
import com.github.yingzhuo.carnival.restful.security.RunAsIdGenerator;
import com.github.yingzhuo.carnival.restful.security.TokenParser;
import com.github.yingzhuo.carnival.restful.security.UserDetailsRealm;
import com.github.yingzhuo.carnival.restful.security.impl.RestfulSecurityInterceptor;
import com.github.yingzhuo.carnival.restful.security.mvc.RestfulSecurityHandlerMethodArgumentResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.PostConstruct;
import java.util.List;

@ConditionalOnWebApplication
@AutoConfigureAfter(RestfulSecurityBeanConfiguration.class)
@Slf4j
public class RestfulSecurityInterceptorConfiguration extends WebMvcConfigurerAdapter {

    private final TokenParser tokenParser;
    private final UserDetailsRealm userDetailsRealm;
    private final AuthenticationListener authenticationListener;
    private final RunAsIdGenerator runAsIdGenerator;

    public RestfulSecurityInterceptorConfiguration(TokenParser tokenParser, UserDetailsRealm userDetailsRealm, AuthenticationListener authenticationListener, RunAsIdGenerator runAsIdGenerator) {
        this.tokenParser = tokenParser;
        this.userDetailsRealm = userDetailsRealm;
        this.authenticationListener = authenticationListener;
        this.runAsIdGenerator = runAsIdGenerator;
    }

    @PostConstruct
    private void init() {
        log.debug("SpringBoot auto-config: {}", getClass().getName());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RestfulSecurityInterceptor(tokenParser, userDetailsRealm, authenticationListener, runAsIdGenerator)).addPathPatterns("/", "/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new RestfulSecurityHandlerMethodArgumentResolver());
    }

}
