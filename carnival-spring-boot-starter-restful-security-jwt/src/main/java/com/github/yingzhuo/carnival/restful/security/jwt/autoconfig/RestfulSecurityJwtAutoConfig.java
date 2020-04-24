/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.security.jwt.autoconfig;

import com.github.yingzhuo.carnival.restful.security.jwt.props.JwtProps;
import com.github.yingzhuo.carnival.restful.security.parser.BearerStringTokenParser;
import com.github.yingzhuo.carnival.restful.security.parser.TokenParser;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;

/**
 * @author 应卓
 */
@Lazy(false)
@ConditionalOnWebApplication
@EnableConfigurationProperties(JwtProps.class)
public class RestfulSecurityJwtAutoConfig {

    @Bean
    @ConditionalOnMissingBean
    public TokenParser tokenParser() {
        return new BearerStringTokenParser();
    }

}
