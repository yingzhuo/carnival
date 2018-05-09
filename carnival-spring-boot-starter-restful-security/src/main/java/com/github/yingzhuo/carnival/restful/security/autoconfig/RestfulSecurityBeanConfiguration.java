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
import com.github.yingzhuo.carnival.restful.security.impl.HttpBasicTokenParser;
import com.github.yingzhuo.carnival.restful.security.impl.SimpleRunAsIdGenerator;
import com.github.yingzhuo.carnival.restful.security.impl.UsernamePasswordUserDetailsRealm;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Slf4j
@ConditionalOnWebApplication
@EnableConfigurationProperties(RestfulSecurityBeanConfiguration.UserProps.class)
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
    public UserDetailsRealm userDetailsRealm(UserProps userProps) {

        if (userProps.getUsername() != null) {
            if (StringUtils.isEmpty(userProps.getPassword())) {
                log.warn("'carnival.restful.security.password' is EMPTY.");
                userProps.setPassword(RandomStringUtils.randomAscii(6));
            }

            log.info("\n\n\t\tUSERNAME: \"{}\"\n", userProps.getUsername());
            log.info("\n\n\t\tPASSWORD: \"{}\"\n", userProps.getPassword());
            return new UsernamePasswordUserDetailsRealm(userProps.isCaseSensitive(), userProps.getUsername(), userProps.getPassword());
        } else {
            return (token) -> Optional.empty();
        }
    }

    @Bean
    @ConditionalOnMissingBean
    public AuthenticationListener authenticationListener() {
        return (userDetails) -> {
        };
    }

    @Bean
    @ConditionalOnMissingBean
    public RunAsIdGenerator runAsIdGenerator() {
        return new SimpleRunAsIdGenerator();
    }

    @Data
    @ConfigurationProperties("carnival.restful.security")
    static class UserProps {
        private boolean caseSensitive = true;
        private String username = "user";
        private String password = "changeme";
    }

}
