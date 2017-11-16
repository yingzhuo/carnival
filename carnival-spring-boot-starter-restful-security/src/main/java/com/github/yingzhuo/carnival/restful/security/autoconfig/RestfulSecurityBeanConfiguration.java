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
import com.github.yingzhuo.carnival.restful.security.impl.AlwaysEmptyUserDetailsRealm;
import com.github.yingzhuo.carnival.restful.security.impl.HttpBasicTokenParser;
import com.github.yingzhuo.carnival.restful.security.impl.NopAuthenticationListener;
import com.github.yingzhuo.carnival.restful.security.impl.SimpleRunAsIdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;

@ConditionalOnWebApplication
@Slf4j
public class RestfulSecurityBeanConfiguration {

    @PostConstruct
    private void init() {
        log.debug("SpringBoot auto-config: {}", getClass().getName());
    }

    @Bean
    @ConditionalOnMissingBean
    public TokenParser tokenParser() {
        return new HttpBasicTokenParser();
    }

    @Bean
    @ConditionalOnMissingBean
    public UserDetailsRealm userDetailsRealm() {
        return new AlwaysEmptyUserDetailsRealm();
    }

    @Bean
    @ConditionalOnMissingBean
    public AuthenticationListener authenticationListener() {
        return new NopAuthenticationListener();
    }

    @Bean
    @ConditionalOnMissingBean
    public RunAsIdGenerator runAsIdGenerator() {
        return new SimpleRunAsIdGenerator();
    }

}
