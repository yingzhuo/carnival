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

import com.github.yingzhuo.carnival.common.condition.ConditionalOnAnyResource;
import com.github.yingzhuo.carnival.common.io.ResourceOptional;
import com.github.yingzhuo.carnival.restful.security.httpbasic.parser.HttpBasicTokenParser;
import com.github.yingzhuo.carnival.restful.security.httpbasic.realm.InMemoryHttpBasicUserDetailsRealm;
import com.github.yingzhuo.carnival.restful.security.parser.TokenParser;
import com.github.yingzhuo.carnival.restful.security.realm.UserDetailsRealm;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author 应卓
 */
@ConditionalOnWebApplication
public class RestfulSecurityHttpBasicAutoConfig {

    private static final String[] DEFAULT_LOCATION = {
            "file:./httpbasic.properties",
            "file:./httpbasic.xml",
            "classpath:/httpbasic.properties",
            "classpath:/httpbasic.xml",
            "classpath:/META-INF/httpbasic.properties",
            "classpath:/META-INF/httpbasic.xml"
    };

    @Bean
    @ConditionalOnMissingBean
    public TokenParser httpBasicTokenParser() {
        return new HttpBasicTokenParser();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnAnyResource(locations = {
            "file:./httpbasic.properties",
            "file:./httpbasic.xml",
            "classpath:/httpbasic.properties",
            "classpath:/httpbasic.xml",
            "classpath:/META-INF/httpbasic.properties",
            "classpath:/META-INF/httpbasic.xml"
    })
    public UserDetailsRealm userDetailsRealm() {
        final InMemoryHttpBasicUserDetailsRealm realm = new InMemoryHttpBasicUserDetailsRealm();

        ResourceOptional option = ResourceOptional.of(DEFAULT_LOCATION);

        if (option.isAbsent()) {
            throw new AssertionError();
        }

        if (option.getLocation().endsWith(".xml")) {
            realm.loadFromXML(option.getInputStream());
        } else {
            realm.load(option.getInputStream());
        }

        option.closeResource();

        return realm;
    }

}
