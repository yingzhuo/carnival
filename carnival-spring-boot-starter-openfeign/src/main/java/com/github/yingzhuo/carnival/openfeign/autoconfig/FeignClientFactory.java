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

import com.github.yingzhuo.carnival.openfeign.Configuration;
import com.github.yingzhuo.carnival.openfeign.props.FeignProperties;
import com.github.yingzhuo.carnival.openfeign.target.CoreTarget;
import com.github.yingzhuo.carnival.openfeign.url.FixedUrlSupplier;
import com.github.yingzhuo.carnival.openfeign.url.GlobalUrlSupplier;
import com.github.yingzhuo.carnival.openfeign.url.UrlSupplier;
import feign.*;
import feign.auth.BasicAuthRequestInterceptor;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.codec.ErrorDecoder;
import feign.slf4j.Slf4jLogger;
import lombok.val;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;

import java.util.Optional;

import static feign.Feign.Builder;

/**
 * @author 应卓
 * @since 1.6.17
 */
class FeignClientFactory<T> implements FactoryBean<T>, InitializingBean, ApplicationContextAware, EnvironmentAware {

    private ApplicationContext applicationContext;
    private Environment environment;

    // setter注入
    private String url;
    private Class<?> urlSupplierType;
    private Class<T> clientType;
    private Class<?> configurationType;

    // 以下为初始化时才处理的字段
    private final Builder builder = new Builder();
    private FeignProperties props = null;

    @Override
    public T getObject() {
        return builder.target(
                new CoreTarget<>(
                        clientType,
                        getUrlSupplier(urlSupplierType, url),
                        environment)
        );
    }

    @Override
    public Class<?> getObjectType() {
        return clientType;
    }

    @Override
    public void afterPropertiesSet() {
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

    private void reset() {
        if (configurationType == void.class) {
            return;
        }

        final Configuration config = (Configuration) applicationContext.getBean(configurationType);

        if (config.decode404()) {
            builder.decode404();
        }

        Optional.ofNullable(config.getCapabilities()).ifPresent(cs -> cs.forEach(builder::addCapability));
        Optional.ofNullable(config.getContract()).ifPresent(builder::contract);
        Optional.ofNullable(config.getEncoder()).ifPresent(builder::encoder);
        Optional.ofNullable(config.getDecoder()).ifPresent(builder::decoder);
        Optional.ofNullable(config.getErrorDecoder()).ifPresent(builder::errorDecoder);
        Optional.ofNullable(config.getRetryer()).ifPresent(builder::retryer);
        Optional.ofNullable(config.getQueryMapEncoder()).ifPresent(builder::queryMapEncoder);
        Optional.ofNullable(config.getRequestInterceptors()).ifPresent(builder::requestInterceptors);
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

    private UrlSupplier getUrlSupplier(Class<?> urlSupplierType, String url) {
        if (!"".equals(url)) {
            return new FixedUrlSupplier(url);
        }

        if (urlSupplierType != void.class) {
            return (UrlSupplier) applicationContext.getBean(urlSupplierType); // 类型转换异常抛出就可以了
        }

        try {
            return applicationContext.getBean(GlobalUrlSupplier.class);
        } catch (BeansException e) {
            throw new IllegalArgumentException("url or urlSupplier not configured");
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        this.props = applicationContext.getBean(FeignProperties.class);
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public void setClientType(Class<T> clientType) {
        this.clientType = clientType;
    }

    public void setUrlSupplierType(Class<?> urlSupplierType) {
        this.urlSupplierType = urlSupplierType;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setConfigurationType(Class<?> configurationType) {
        this.configurationType = configurationType;
    }

}
