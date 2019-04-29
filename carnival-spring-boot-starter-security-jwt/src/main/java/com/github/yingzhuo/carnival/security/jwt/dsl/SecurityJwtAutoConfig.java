/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.security.jwt.dsl;

import com.github.yingzhuo.carnival.security.jwt.JwtTokenParser;
import com.github.yingzhuo.carnival.security.jwt.algorithm.SignatureAlgorithm;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * @author 应卓
 */
@ConditionalOnWebApplication
@EnableConfigurationProperties(SecurityJwtAutoConfig.Props.class)
public class SecurityJwtAutoConfig {

    @Bean
    @ConditionalOnMissingBean
    public JwtTokenParser jwtTokenParser() {
        return new JwtTokenParser();
    }

    // -----------------------------------------------------------------------------------------------------------

    @Getter
    @Setter
    @ConfigurationProperties(prefix = "carnival.security.jwt")
    public static class Props {

        private SignatureAlgorithm algorithm = SignatureAlgorithm.HMAC512;;

        private String secret = getClass().getName();
    }

}
