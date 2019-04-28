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

import lombok.val;
import org.springframework.context.ApplicationContext;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

/**
 * @author 应卓
 */
public class JwtCustomDSL extends AbstractHttpConfigurer<JwtCustomDSL, HttpSecurity> {

    @Override
    public void init(HttpSecurity http) throws Exception {
        http.csrf().disable();
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        val spring = http.getSharedObject(ApplicationContext.class);
    }

}
