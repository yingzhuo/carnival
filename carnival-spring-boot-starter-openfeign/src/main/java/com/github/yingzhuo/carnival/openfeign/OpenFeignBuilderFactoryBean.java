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

import com.github.yingzhuo.carnival.openfeign.props.OpenFeignProps;
import feign.Capability;
import feign.Client;
import feign.RequestInterceptor;
import feign.Retryer;
import feign.auth.BasicAuthRequestInterceptor;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.codec.ErrorDecoder;
import feign.slf4j.Slf4jLogger;
import feign.spring.SpringContract;
import lombok.val;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.List;

import static feign.Feign.Builder;
import static feign.Request.Options;


/**
 * @author 应卓
 * @since 1.6.16
 */
public class OpenFeignBuilderFactoryBean implements FactoryBean<Builder>, ApplicationContextAware, InitializingBean {

    private ApplicationContext applicationContext;

    private final Builder builder; // instance

    @Autowired
    private OpenFeignProps props; // never null

    @Autowired(required = false)
    private List<RequestInterceptor> interceptors; // may be null

    @Autowired(required = false)
    private ErrorDecoder errorDecoder; // may be null

    @Autowired(required = false)
    private List<Capability> capabilities; // may be null

    @Autowired
    private Retryer retryer; // never null

    @Autowired
    private Options options; // never null

    public OpenFeignBuilderFactoryBean() {
        this(null);
    }

    public <T extends Builder> OpenFeignBuilderFactoryBean(T builder) {
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
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
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
        initBasicAuth();
        initRetryer();
        initCapabilities();
        initOptions();
    }

    private void initClient() {
        try {
            val client = applicationContext.getBean(Client.class);
            builder.client(client);
        } catch (BeansException e) {
            // 找不到就用默认实现
        }
    }

    private void initLogger() {
        builder.logger(new Slf4jLogger(props.getLoggerName()));
        builder.logLevel(props.getLoggerLevel());
    }

    private void initEncoder() {
        val encoder = applicationContext.getBean(Encoder.class);
        builder.encoder(encoder);
    }

    private void initDecoder() {
        val decoder = applicationContext.getBean(Decoder.class);
        builder.decoder(decoder);
    }

    private void initErrorDecoder() {
        if (errorDecoder != null) {
            builder.errorDecoder(errorDecoder);
        }
    }

    private void initAnnotationStyle() {
        if (props.getAnnotationStyle() == OpenFeignProps.AnnotationStyle.SPRING) {
            val contract = applicationContext.getBean(SpringContract.class);
            builder.contract(contract);
        }
    }

    private void initInterceptors() {
        if (interceptors != null) {
            builder.requestInterceptors(interceptors);
        }
    }

    private void init404() {
        if (applicationContext.getBean(OpenFeignProps.class).isDecode404()) {
            builder.decode404();
        }
    }

    private void initBasicAuth() {
        try {
            val basicAuth = applicationContext.getBean(OpenFeignProps.class).getBasicAuth();
            val username = basicAuth.getUsername();
            val password = basicAuth.getPassword();
            if (basicAuth.getUsername() != null && basicAuth.getPassword() != null) {
                builder.requestInterceptor(new BasicAuthRequestInterceptor(username, password));
            }
        } catch (BeansException | NullPointerException e) {
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

}
