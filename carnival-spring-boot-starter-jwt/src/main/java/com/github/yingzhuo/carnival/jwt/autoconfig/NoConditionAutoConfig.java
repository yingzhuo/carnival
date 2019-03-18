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

import com.github.yingzhuo.carnival.jwt.RequiresJwt;
import com.github.yingzhuo.carnival.jwt.props.JwtProps;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

/**
 * @author 应卓
 */
@ConditionalOnWebApplication
@EnableConfigurationProperties(JwtProps.class)
public class NoConditionAutoConfig {

    @Bean
    @Primary
    public RequiresJwt.AuthComponent requiresJwtAuthenticationComponent() {
        return new RequiresJwt.AuthComponent();
    }

}
