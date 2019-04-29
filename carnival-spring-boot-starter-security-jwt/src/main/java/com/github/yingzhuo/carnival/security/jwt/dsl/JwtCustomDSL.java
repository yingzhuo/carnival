/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.security.jwt.dsl;

import com.github.yingzhuo.carnival.security.jwt.AbstractJwtAuthenticationManager;
import com.github.yingzhuo.carnival.security.jwt.JwtAuthenticationFailedEntryPoint;
import com.github.yingzhuo.carnival.security.jwt.JwtAuthenticationFilter;
import com.github.yingzhuo.carnival.security.jwt.JwtTokenParser;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 * @author 应卓
 */
public class JwtCustomDSL extends AbstractHttpConfigurer<JwtCustomDSL, HttpSecurity> {

    @Autowired
    private SecurityJwtAutoConfig.Props props;

    @Override
    public void init(HttpSecurity http) throws Exception {
        http.csrf().disable();
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        val spring = http.getSharedObject(ApplicationContext.class);

        val parser = spring.getBean(JwtTokenParser.class);

        val entryPoint = spring.getBean(JwtAuthenticationFailedEntryPoint.class);

        val manager = spring.getBean(AbstractJwtAuthenticationManager.class);
        manager.setSecret(props.getSecret());
        manager.setSignatureAlgorithm(props.getAlgorithm());

        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(parser, manager, entryPoint);
        filter.afterPropertiesSet();

        http.addFilterBefore(filter, BasicAuthenticationFilter.class);
    }

}
