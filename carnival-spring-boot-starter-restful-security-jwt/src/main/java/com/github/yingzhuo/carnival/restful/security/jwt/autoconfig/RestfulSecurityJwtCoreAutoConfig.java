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

import com.auth0.jwt.algorithms.Algorithm;
import com.github.yingzhuo.carnival.restful.security.jwt.signature.AlgorithmFactories;
import com.github.yingzhuo.carnival.restful.security.jwt.signature.AlgorithmFactory;
import com.github.yingzhuo.carnival.restful.security.parser.HttpHeaderTokenParser;
import com.github.yingzhuo.carnival.restful.security.parser.TokenParser;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;

/**
 * @author 应卓
 */
@Lazy(false)
@ConditionalOnWebApplication
public class RestfulSecurityJwtCoreAutoConfig {

    @Bean
    @ConditionalOnMissingBean
    public AlgorithmFactory algorithmFactory() {
        return AlgorithmFactories.hmac512(Algorithm.class.getName());
    }

    @Bean
    @ConditionalOnMissingBean
    public TokenParser tokenParser() {
        return new HttpHeaderTokenParser(HttpHeaders.AUTHORIZATION, "Bearer ");
    }

}
