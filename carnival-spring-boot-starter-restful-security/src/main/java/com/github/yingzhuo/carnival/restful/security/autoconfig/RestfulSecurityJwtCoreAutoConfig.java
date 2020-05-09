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

import com.github.yingzhuo.carnival.restful.security.factory.AlgorithmFactory;
import com.github.yingzhuo.carnival.restful.security.factory.DefaultJwtTokenFactory;
import com.github.yingzhuo.carnival.restful.security.factory.JwtTokenFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;

/**
 * @author 应卓
 */
@Lazy(false)
public class RestfulSecurityJwtCoreAutoConfig {

    @Autowired(required = false)
    private AlgorithmFactory algFactory;

    @Bean
    public JwtTokenFactory tokenFactory(AlgorithmFactory algFactory) {

        if (algFactory == null) {
            throw new NullPointerException("AlgorithmFactory NOT configured.");
        }

        return new DefaultJwtTokenFactory(algFactory.create());
    }

}
