/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.jwt2.autoconfig;

import com.github.yingzhuo.carnival.jwt2.factory.DefaultTokenFactory;
import com.github.yingzhuo.carnival.jwt2.factory.TokenFactory;
import com.github.yingzhuo.carnival.jwt2.props.JwtProps;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * @author 应卓
 */
@EnableConfigurationProperties(JwtProps.class)
public class TokenFactoryConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public TokenFactory tokenFactory(JwtProps props) {
        DefaultTokenFactory factory = new DefaultTokenFactory();
        factory.setSecret(props.getSecret());
        factory.setSignatureAlgorithm(props.getSignatureAlgorithm());
        return factory;
    }

}
