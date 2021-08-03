/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.security.autoconfig;

import com.auth0.jwt.algorithms.Algorithm;
import com.github.yingzhuo.carnival.security.jwt.factory.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * @author 应卓
 * @since 1.10.2
 */
class JwtFactoryAutoConfig {

    @Autowired(required = false)
    private JwtIdFactory jwtIdFactory;

    @Autowired(required = false)
    private KeyIdFactory keyIdFactory;

    @Autowired(required = false)
    private JwtTokenMetadataDefaults jwtTokenMetadataDefaults;

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean(Algorithm.class)
    JwtTokenFactory jwtTokenFactory(Algorithm algorithm) {
        return new JwtTokenFactoryImpl(algorithm, jwtTokenMetadataDefaults, jwtIdFactory, keyIdFactory);
    }

}
