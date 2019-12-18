/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.security.httpbasic.autoconfig;

import com.github.yingzhuo.carnival.restful.security.httpbasic.parser.HttpBasicTokenParser;
import com.github.yingzhuo.carnival.restful.security.parser.TokenParser;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author 应卓
 */
@ConditionalOnWebApplication
public class RestfulSecurityHttpBasicAutoConfig {

    @Bean
    @ConditionalOnMissingBean
    public TokenParser httpBasicTokenParser() {
        return new HttpBasicTokenParser();
    }

}
