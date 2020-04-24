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

import com.github.yingzhuo.carnival.restful.security.jwt.factory.DefaultJwtTokenFactory;
import com.github.yingzhuo.carnival.restful.security.jwt.factory.JwtTokenFactory;
import com.github.yingzhuo.carnival.restful.security.jwt.props.JwtProps;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;

/**
 * @author 应卓
 */
@Lazy(false)
public class JwtTokenFactoryAutoConfig {

    @Bean
    @ConditionalOnMissingBean
    public JwtTokenFactory tokenFactory(JwtProps props) {
        return new DefaultJwtTokenFactory(props.getAlgorithm());
    }

}
