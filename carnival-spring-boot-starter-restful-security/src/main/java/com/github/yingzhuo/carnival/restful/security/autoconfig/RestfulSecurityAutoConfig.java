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

import com.github.yingzhuo.carnival.restful.security.cache.CacheManager;
import com.github.yingzhuo.carnival.restful.security.cache.NopCacheManager;
import com.github.yingzhuo.carnival.restful.security.listener.AuthenticationListener;
import com.github.yingzhuo.carnival.restful.security.listener.NopAuthenticationListener;
import com.github.yingzhuo.carnival.restful.security.parser.HttpBasicTokenParser;
import com.github.yingzhuo.carnival.restful.security.parser.TokenParser;
import com.github.yingzhuo.carnival.restful.security.realm.UserDetailsRealm;
import com.github.yingzhuo.carnival.restful.security.realm.UsernamePasswordUserDetailsRealm;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.util.StringUtils;

import java.util.Optional;

/**
 * @author 应卓
 */
@Slf4j
@ConditionalOnWebApplication
@EnableConfigurationProperties(RestfulSecurityAutoConfig.UserProps.class)
public class RestfulSecurityAutoConfig {

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
        return new NopAuthenticationListener();
    }

    @ConditionalOnMissingBean
    @Bean(name = "restfulSecurityCacheManager")
    public CacheManager cacheManager() {
        return new NopCacheManager();
    }

    @Getter
    @Setter
    @ConfigurationProperties("carnival.restful.security")
    static class UserProps {
        private boolean caseSensitive = true;
        private String username = "user";
        private String password = "change-me";
    }

}
