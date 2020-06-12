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

import com.github.yingzhuo.carnival.openfeign.interceptor.OpenFeignRequestInterceptor;
import com.github.yingzhuo.carnival.openfeign.props.OpenFeignProps;
import feign.Client;
import feign.RequestInterceptor;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.slf4j.Slf4jLogger;
import feign.spring.SpringContract;
import lombok.val;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static feign.Feign.Builder;

/**
 * @author 应卓
 * @since 1.6.16
 */
public class OpenFeignBuilderFactoryBean implements FactoryBean<Builder>, ApplicationContextAware, InitializingBean {

    private ApplicationContext applicationContext;
    private final Builder basic;

    @Autowired(required = false)
    private List<OpenFeignRequestInterceptor> interceptors;

    public OpenFeignBuilderFactoryBean() {
        this(null);
    }

    public OpenFeignBuilderFactoryBean(Builder basic) {
        this.basic = basic != null ? basic : new Builder();
    }

    @Override
    public Builder getObject() {
        return basic;
    }

    @Override
    public Class<?> getObjectType() {
        return Builder.class;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() {
        if (interceptors == null) {
            interceptors = Collections.emptyList();
        }

        initClient();
        initLogger();
        initInterceptors();
        initAnnotationStyle();
        initEncoder();
        initDecoder();
    }

    private void initClient() {
        try {
            val client = applicationContext.getBean(Client.class);
            basic.client(client);
        } catch (NoSuchBeanDefinitionException e) {
            // NoOP
        }
    }

    private void initLogger() {
        val props = applicationContext.getBean(OpenFeignProps.class);
        basic.logger(new Slf4jLogger(props.getLoggerName()));
        basic.logLevel(props.getLoggerLevel());
    }

    private void initEncoder() {
        val encoder = applicationContext.getBean(Encoder.class);
        basic.encoder(encoder);
    }

    private void initDecoder() {
        val decoder = applicationContext.getBean(Decoder.class);
        basic.decoder(decoder);
    }

    private void initAnnotationStyle() {
        val props = applicationContext.getBean(OpenFeignProps.class);

        if (props.getAnnotationStyle() == OpenFeignProps.AnnotationStyle.SPRING) {
            val contract = applicationContext.getBean(SpringContract.class);
            basic.contract(contract);
        }
    }

    private void initInterceptors() {
        basic.requestInterceptors(
                interceptors.stream()
                        .map(it -> (RequestInterceptor) it)
                        .collect(Collectors.toList())
        );
    }

}
