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

import com.github.yingzhuo.carnival.websecret.ImportSelectorConfigHolder;
import com.github.yingzhuo.carnival.websecret.dao.PropertiesSecretLoader;
import com.github.yingzhuo.carnival.websecret.dao.SecretLoader;
import com.github.yingzhuo.carnival.websecret.matcher.DefaultSignatureMatcher;
import com.github.yingzhuo.carnival.websecret.matcher.SignatureMatcher;
import com.github.yingzhuo.carnival.websecret.mvc.WebSecretInterceptor;
import com.github.yingzhuo.carnival.websecret.parser.*;
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
    private ClientIdParser clientIdParser;

    @Autowired
    private TimestampParser timestampParser;

    @Autowired
    private SignatureParser signatureParser;

    @Autowired
    private SecretLoader secretLoader;

    @Autowired
    private SignatureMatcher signatureMatcher;

    @Bean
    @ConditionalOnMissingBean
    public NonceParser nonceParser() {
        return new DefaultNonceParser();
    }

    @Bean
    @ConditionalOnMissingBean
    public ClientIdParser clientIdParser() {
        return new DefaultClientIdParser();
    }

    @Bean
    @ConditionalOnMissingBean
    public SignatureParser signatureParser() {
        return new DefaultSignatureParser();
    }

    @Bean
    @ConditionalOnMissingBean
    public TimestampParser timestampParser() {
        return new DefaultTimestampParser();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnResource(resources = {"classpath:/websecret.properties"})
    public SecretLoader secretLoader() {
        return new PropertiesSecretLoader();
    }

    @Bean
    @ConditionalOnMissingBean
    public SignatureMatcher signatureMatcher() {
        return new DefaultSignatureMatcher();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        WebSecretInterceptor interceptor = new WebSecretInterceptor();
        interceptor.setClientIdParser(clientIdParser);
        interceptor.setNonceParser(nonceParser);
        interceptor.setSignatureParser(signatureParser);
        interceptor.setTimestampParser(timestampParser);
        interceptor.setSignatureMatcher(signatureMatcher);
        interceptor.setSecretLoader(secretLoader);
        registry.addInterceptor(interceptor).addPathPatterns("/", "/**").order(ImportSelectorConfigHolder.interceptorOrder);
    }

}
