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

import com.github.yingzhuo.carnival.restful.security.jwt.signature.AlgorithmFactory;
import com.github.yingzhuo.carnival.restful.security.jwt.signature.HMAC512AlgorithmFactory;
import com.github.yingzhuo.carnival.restful.security.parser.CompositeTokenParser;
import com.github.yingzhuo.carnival.restful.security.parser.HttpHeaderTokenParser;
import com.github.yingzhuo.carnival.restful.security.parser.HttpParameterTokenParser;
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
public class JwtCoreAutoConfig {

    @Bean
    @ConditionalOnMissingBean
    public AlgorithmFactory jwtAlgorithmFactory() {
        return new HMAC512AlgorithmFactory();
    }

    @Bean
    @ConditionalOnMissingBean
    public TokenParser tokenParser() {
        return new CompositeTokenParser(
                new HttpHeaderTokenParser(HttpHeaders.AUTHORIZATION, "Bearer "),
                new HttpParameterTokenParser("_jwt_token")
        );
    }

}
