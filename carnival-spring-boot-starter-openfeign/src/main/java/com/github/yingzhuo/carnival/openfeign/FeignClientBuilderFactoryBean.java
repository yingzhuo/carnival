/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.openfeign;

import com.github.yingzhuo.carnival.openfeign.props.FeignProperties;
import feign.*;
import feign.auth.BasicAuthRequestInterceptor;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.codec.ErrorDecoder;
import feign.slf4j.Slf4jLogger;
import lombok.val;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

import static feign.Feign.Builder;
import static feign.Request.Options;

/**
 * @author 应卓
 * @since 1.6.16
 */
public class FeignClientBuilderFactoryBean implements FactoryBean<Builder>, InitializingBean {

    /**
     * @see #reset()
     */
    private final Builder builder;

    @Autowired(required = false)
    private Encoder encoder;

    @Autowired(required = false)
    private Decoder decoder;

    @Autowired
    private FeignProperties props;

    @Autowired(required = false)
    private List<RequestInterceptor> interceptors;

    @Autowired(required = false)
    private ErrorDecoder errorDecoder;

    @Autowired(required = false)
    private List<Capability> capabilities;

    @Autowired
    private Retryer retryer;

    @Autowired
    private Options options;

    @Autowired
    private Contract contract;

    @Autowired(required = false)
    private Client client;

    @Autowired(required = false)
    private FeignBuilderCustomizer customizer;

    public FeignClientBuilderFactoryBean() {
        this(null);
    }

    public <T extends Builder> FeignClientBuilderFactoryBean(T builder) {
        this.builder = builder != null ? builder : new Builder();
    }

    @Override
    public Builder getObject() {
        return builder;
    }

    @Override
    public Class<?> getObjectType() {
        return Builder.class;
    }

    @Override
    public void afterPropertiesSet() {
        initClient();
        initLogger();
        initInterceptors();
        initAnnotationStyle();
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

    private void initClient() {
        Optional.ofNullable(client).ifPresent(builder::client);
    }

    private void initLogger() {
        builder.logger(new Slf4jLogger(props.getLoggerName()));
        builder.logLevel(props.getLoggerLevel());
    }

    private void initEncoder() {
        Optional.ofNullable(encoder).ifPresent(builder::encoder);
    }

    private void initDecoder() {
        Optional.ofNullable(decoder).ifPresent(builder::decoder);
    }

    private void initErrorDecoder() {
        Optional.ofNullable(errorDecoder).ifPresent(builder::errorDecoder);
    }

    private void initAnnotationStyle() {
        builder.contract(contract);
    }

    private void initInterceptors() {
        Optional.ofNullable(interceptors).ifPresent(builder::requestInterceptors);
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
        } catch (NullPointerException e) {
            // NoOP
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
        } catch (NullPointerException e) {
            // NoOP
        }
    }

    private void initRetryer() {
        builder.retryer(retryer);
    }

    private void initCapabilities() {
        if (capabilities != null) {
            for (val init : capabilities) {
                builder.addCapability(init);
            }
        }
    }

    private void initOptions() {
        builder.options(options);
    }

    private void reset() {
        if (customizer != null) {
            customizer.customize(builder);
        }
    }

}
