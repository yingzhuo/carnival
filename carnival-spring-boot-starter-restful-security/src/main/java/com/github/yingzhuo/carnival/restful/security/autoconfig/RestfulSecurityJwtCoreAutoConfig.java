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

import com.auth0.jwt.algorithms.Algorithm;
import com.github.yingzhuo.carnival.restful.security.factory.AlgorithmFactories;
import com.github.yingzhuo.carnival.restful.security.factory.AlgorithmFactory;
import com.github.yingzhuo.carnival.restful.security.factory.DefaultJwtTokenFactory;
import com.github.yingzhuo.carnival.restful.security.factory.JwtTokenFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;

/**
 * @author 应卓
 */
@Lazy(false)
public class RestfulSecurityJwtCoreAutoConfig {

    @Bean
    @ConditionalOnMissingBean
    public AlgorithmFactory algorithmFactory() {
        return AlgorithmFactories.hmac512(Algorithm.class.getName());
    }

    @Bean
    @ConditionalOnMissingBean
    public JwtTokenFactory tokenFactory(AlgorithmFactory algFactory) {
        return new DefaultJwtTokenFactory(algFactory.create());
    }

}
