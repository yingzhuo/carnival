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

import com.github.yingzhuo.carnival.jwt.factory.DefaultJwtTokenFactory;
import com.github.yingzhuo.carnival.jwt.factory.JwtTokenFactory;
import com.github.yingzhuo.carnival.jwt.props.JwtProps;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * @author 应卓
 */
public class TokenFactoryConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public JwtTokenFactory tokenFactory(JwtProps props) {
        DefaultJwtTokenFactory factory = new DefaultJwtTokenFactory();
        factory.setSecret(props.getSecret());
        factory.setSignatureAlgorithm(props.getSignatureAlgorithm());
        return factory;
    }

}
