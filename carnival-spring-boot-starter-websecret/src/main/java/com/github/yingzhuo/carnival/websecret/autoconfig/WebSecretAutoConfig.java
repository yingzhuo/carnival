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

import com.github.yingzhuo.carnival.common.condition.ConditionalOnAnyResource;
import com.github.yingzhuo.carnival.common.io.ResourceOption;
import com.github.yingzhuo.carnival.websecret.EnableWebSecret;
import com.github.yingzhuo.carnival.websecret.ValidationStrategy;
import com.github.yingzhuo.carnival.websecret.WebSecretContext;
import com.github.yingzhuo.carnival.websecret.dao.PropertiesSecretLoader;
import com.github.yingzhuo.carnival.websecret.dao.SecretLoader;
import com.github.yingzhuo.carnival.websecret.matcher.DefaultSignatureMatcher;
import com.github.yingzhuo.carnival.websecret.matcher.SignatureMatcher;
import com.github.yingzhuo.carnival.websecret.mvc.WebSecretHolder;
import com.github.yingzhuo.carnival.websecret.mvc.WebSecretInterceptor;
import com.github.yingzhuo.carnival.websecret.parser.*;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
import java.util.Optional;

/**
 * @author 应卓
 */
@ConditionalOnWebApplication
public class WebSecretAutoConfig implements WebMvcConfigurer {

    @Autowired(required = false)
    private LocaleResolver localeResolver;

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
    @ConditionalOnAnyResource(resources = {
            "file:./websecret.properties",
            "file:./web-secret.properties",
            "classpath:/websecret.properties",
            "classpath:/web-secret.properties",
            "classpath:/META-INF/websecret.properties",
            "classpath:/META-INF/web-secret.properties"
    })
    public SecretLoader secretLoader() {
        val rp = ResourceOption.of(
                "file:./websecret.properties",
                "file:./web-secret.properties",
                "classpath:/websecret.properties",
                "classpath:/web-secret.properties",
                "classpath:/META-INF/websecret.properties",
                "classpath:/META-INF/web-secret.properties");

        return new PropertiesSecretLoader(rp.get());
    }

    @Bean
    @ConditionalOnMissingBean
    public SignatureMatcher signatureMatcher() {
        return new DefaultSignatureMatcher();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        val validationStrategy = EnableWebSecret.WebSecretImportSelector
                .getConfig("validationStrategy", ValidationStrategy.class);

        WebSecretInterceptor interceptor = new WebSecretInterceptor();
        Optional.ofNullable(localeResolver).ifPresent(interceptor::setLocaleResolver);
        interceptor.setClientIdParser(clientIdParser);
        interceptor.setNonceParser(nonceParser);
        interceptor.setSignatureParser(signatureParser);
        interceptor.setTimestampParser(timestampParser);
        interceptor.setSignatureMatcher(signatureMatcher);
        interceptor.setSecretLoader(secretLoader);
        interceptor.setValidationStrategy(validationStrategy);

        registry.addInterceptor(interceptor)
                .addPathPatterns("/", "/**")
                .order(EnableWebSecret.WebSecretImportSelector.getConfig("interceptorOrder", Integer.class));
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new HandlerMethodArgumentResolver() {

            @Override
            public boolean supportsParameter(MethodParameter parameter) {
                return parameter.getParameterType() == WebSecretContext.class;
            }

            @Override
            public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
                return WebSecretHolder.get();
            }
        });
    }

}
