/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.websecret.autoconfig;

import com.github.yingzhuo.carnival.websecret.dao.PropertiesSecretLoader;
import com.github.yingzhuo.carnival.websecret.dao.SecretLoader;
import com.github.yingzhuo.carnival.websecret.mvc.WebSecretInterceptor;
import com.github.yingzhuo.carnival.websecret.parser.NonceParser;
import com.github.yingzhuo.carnival.websecret.parser.AppIdParser;
import com.github.yingzhuo.carnival.websecret.parser.SignatureParser;
import com.github.yingzhuo.carnival.websecret.parser.TimestampParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnResource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 应卓
 */
@ConditionalOnWebApplication
public class WebSecretConfiguration implements WebMvcConfigurer {

    @Autowired
    private NonceParser nonceParser;

    @Autowired
    private AppIdParser appIdParser;

    @Autowired
    private TimestampParser timestampParser;

    @Autowired
    private SignatureParser signatureParser;

    @Autowired
    private SecretLoader secretLoader;

    @Bean
    @ConditionalOnMissingBean
    public NonceParser nonceParser() {
        return NonceParser.DEFAULT;
    }

    @Bean
    @ConditionalOnMissingBean
    public AppIdParser appIdParser() {
        return AppIdParser.DEFAULT;
    }

    @Bean
    @ConditionalOnMissingBean
    public SignatureParser signatureParser() {
        return SignatureParser.DEFAULT;
    }

    @Bean
    @ConditionalOnMissingBean
    public TimestampParser timestampParser() {
        return TimestampParser.DEFAULT;
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnResource(resources = {"classpath:/websecret.properties"})
    public SecretLoader secretLoader() {
        return new PropertiesSecretLoader();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        WebSecretInterceptor interceptor = new WebSecretInterceptor();
        interceptor.setAppIdParser(appIdParser);
        interceptor.setNonceParser(nonceParser);
        interceptor.setTimestampParser(timestampParser);
        interceptor.setSignatureParser(signatureParser);
        interceptor.setSecretLoader(secretLoader);
        registry.addInterceptor(interceptor).addPathPatterns("/", "/**");
    }
}
