package com.github.yingzhuo.carnival.jwt2.autoconfig;

import com.github.yingzhuo.carnival.jwt2.factory.DefaultTokenFactory;
import com.github.yingzhuo.carnival.jwt2.factory.TokenFactory;
import com.github.yingzhuo.carnival.jwt2.props.TokenFactoryProps;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * @author 应卓
 */
@EnableConfigurationProperties(TokenFactoryProps.class)
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
