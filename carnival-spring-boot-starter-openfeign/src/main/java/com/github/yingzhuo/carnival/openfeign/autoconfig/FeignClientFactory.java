/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.openfeign.autoconfig;

import com.github.yingzhuo.carnival.openfeign.FeignBuilderCustomizer;
import com.github.yingzhuo.carnival.openfeign.props.FeignProperties;
import com.github.yingzhuo.carnival.openfeign.target.BrokenUrlSupplier;
import com.github.yingzhuo.carnival.openfeign.target.CoreTarget;
import com.github.yingzhuo.carnival.openfeign.target.UrlSupplier;
import com.github.yingzhuo.carnival.resilience4j.config.ConfigHolder;
import com.github.yingzhuo.carnival.resilience4j.util.FeignDecoratorsUtils;
import feign.*;
import feign.auth.BasicAuthRequestInterceptor;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.codec.ErrorDecoder;
import feign.slf4j.Slf4jLogger;
import io.github.resilience4j.feign.Resilience4jFeign;
import lombok.val;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;

import java.util.LinkedList;
import java.util.List;

import static feign.Feign.Builder;

/**
 * @author 应卓
 * @since 1.6.17
 */
class FeignClientFactory<T> implements FactoryBean<T>, ApplicationContextAware {

    private ApplicationContext applicationContext;

    // setter注入
    private Class<T> clientType;
    private Class<? extends UrlSupplier> urlSupplierType;
    private String url;
    private String backend;

    // 以下为初始化时才处理的字段
    private Builder builder = null;
    private FeignProperties props = null;

    @Override
    public T getObject() {
        initBuilder();
        return builder.target(CoreTarget.of(clientType, getUrlSupplier(urlSupplierType, url)));
    }

    @Override
    public Class<?> getObjectType() {
        return clientType;
    }

    private void initBuilder() {
        builder = createBasicBuilder();
        props = applicationContext.getBean(FeignProperties.class);

        initClient();
        initLogger();
        initInterceptors();
        initContract();
        initEncoder();
        initDecoder();
        initErrorDecoder();
        init404();
        initRetryer();
        initCapabilities();
        initOptions();
        initBasicAuth();
        initBearerAuth();
        reset();
    }

    private Builder createBasicBuilder() {
        if ("".equals(backend)) {
            return new Builder();
        }

        final ConfigHolder configHolder = getConfigHolder();
        if (configHolder == null || configHolder.isEmpty()) {
            return new Builder();
        } else {
            return Resilience4jFeign.builder(FeignDecoratorsUtils.getDecorators(backend, configHolder));
        }
    }

    private ConfigHolder getConfigHolder() {
        try {
            return applicationContext.getBean(ConfigHolder.class);
        } catch (BeansException ignored) {
            return null;
        }
    }

    private void initClient() {
        try {
            builder.client(applicationContext.getBean(Client.class));
        } catch (BeansException ignored) {
        }
    }

    private void initLogger() {
        builder.logger(new Slf4jLogger(props.getLoggerName()));
        builder.logLevel(props.getLoggerLevel());
    }

    private void initEncoder() {
        try {
            builder.encoder(applicationContext.getBean(Encoder.class));
        } catch (BeansException ignored) {
        }
    }

    private void initDecoder() {
        try {
            builder.decoder(applicationContext.getBean(Decoder.class));
        } catch (BeansException ignored) {
        }
    }

    private void initErrorDecoder() {
        try {
            builder.errorDecoder(applicationContext.getBean(ErrorDecoder.class));
        } catch (BeansException ignored) {
        }
    }

    private void initContract() {
        try {
            builder.contract(applicationContext.getBean(Contract.class));
        } catch (BeansException ignored) {
        }
    }

    private void initInterceptors() {
        try {
            builder.requestInterceptors(applicationContext.getBeansOfType(RequestInterceptor.class).values());
        } catch (BeansException ignored) {
        }
    }

    private void init404() {
        if (props.isDecode404()) {
            builder.decode404();
        }
    }

    private void initBasicAuth() {
        try {
            val basicAuth = props.getBasicAuth();
            val username = basicAuth.getUsername();
            val password = basicAuth.getPassword();
            val charset = basicAuth.getCharset();
            if (basicAuth.getUsername() != null && basicAuth.getPassword() != null) {
                val interceptor = new BasicAuthRequestInterceptor(username, password, charset);
                builder.requestInterceptor(interceptor);
            }
        } catch (NullPointerException ignored) {
        }
    }

    private void initBearerAuth() {
        try {
            val token = props.getBearerAuth().getToken();
            if (StringUtils.hasText(token)) {
                val interceptor = new RequestInterceptor() {
                    @Override
                    public void apply(RequestTemplate template) {
                        final String headerValue;

                        if (!token.startsWith("Bearer ")) {
                            headerValue = "Bearer " + token;
                        } else {
                            headerValue = token;
                        }

                        template.header(HttpHeaders.AUTHORIZATION, headerValue);
                    }
                };
                builder.requestInterceptor(interceptor);
            }
        } catch (NullPointerException ignored) {
        }
    }

    private void initRetryer() {
        try {
            builder.retryer(applicationContext.getBean(Retryer.class));
        } catch (BeansException ignored) {
        }
    }

    private void initCapabilities() {
        try {
            for (Capability c : applicationContext.getBeansOfType(Capability.class).values()) {
                builder.addCapability(c);
            }
        } catch (BeansException ignored) {
        }
    }

    private void initOptions() {
        try {
            builder.options(applicationContext.getBean(Request.Options.class));
        } catch (BeansException ignored) {
        }
    }

    private void reset() {
        try {
            List<FeignBuilderCustomizer> customizers = new LinkedList<>(applicationContext.getBeansOfType(FeignBuilderCustomizer.class).values());
            customizers.forEach(it -> it.customize(clientType, builder));
        } catch (BeansException ignored) {
        }
    }

    private UrlSupplier getUrlSupplier(Class<? extends UrlSupplier> urlSupplierType, String url) {
        if (!"".equals(url)) {
            return UrlSupplier.of(url);
        }

        if (urlSupplierType != BrokenUrlSupplier.class) {
            return applicationContext.getBean(urlSupplierType);
        }

        throw new IllegalArgumentException();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public void setClientType(Class<T> clientType) {
        this.clientType = clientType;
    }

    public void setUrlSupplierType(Class<? extends UrlSupplier> urlSupplierType) {
        this.urlSupplierType = urlSupplierType;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setBackend(String backend) {
        this.backend = backend;
    }

}
