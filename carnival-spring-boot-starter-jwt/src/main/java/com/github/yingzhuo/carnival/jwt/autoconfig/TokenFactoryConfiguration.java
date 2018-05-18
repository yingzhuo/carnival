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

import com.github.yingzhuo.carnival.jwt.TokenFactory;
import com.github.yingzhuo.carnival.jwt.impl.DefaultTokenFactory;
import com.github.yingzhuo.carnival.jwt.props.TokenFactoryProps;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * @author 应卓
 */
@EnableConfigurationProperties(TokenFactoryProps.class)
@ConditionalOnWebApplication
@ConditionalOnProperty(prefix = "carnival.jwt.token-factory", name = "enabled", havingValue = "true", matchIfMissing = true)
public class TokenFactoryConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public TokenFactory tokenFactory(TokenFactoryProps props) {
        DefaultTokenFactory factory = new DefaultTokenFactory();
        factory.setSecret(props.getSecret());
        factory.setSignatureAlgorithm(props.getSignatureAlgorithm());
        return factory;
    }

}
