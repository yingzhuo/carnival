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

import com.github.yingzhuo.carnival.openfeign.FeignClientBuilderFactoryBean;
import com.github.yingzhuo.carnival.openfeign.props.FeignProperties;
import com.github.yingzhuo.carnival.openfeign.retryer.NeverRetryer;
import com.github.yingzhuo.carnival.openfeign.support.SpringContract;
import feign.Contract;
import feign.Retryer;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;

import static feign.Feign.Builder;
import static feign.Request.Options;

/**
 * @author 应卓
 * @since 1.6.16
 */
@Lazy(false)
@EnableConfigurationProperties(FeignProperties.class)
public class FeignCoreAutoConfig {

    @Bean
    @ConditionalOnMissingBean(Builder.class)
    public FeignClientBuilderFactoryBean defaultFeignClientBuilder() {
        return new FeignClientBuilderFactoryBean();
    }

    @Bean
    @ConditionalOnMissingBean
    public Encoder defaultFeignEncoder() {
        return new JacksonEncoder();
    }

    @Bean
    @ConditionalOnMissingBean
    public Decoder defaultFeignDecoder() {
        return new JacksonDecoder();
    }

    @Bean
    @ConditionalOnMissingBean
    public Retryer defaultFeignRetryer() {
        return NeverRetryer.INSTANCE;
    }

    @Bean
    @ConditionalOnMissingBean
    public Options defaultFeignOptions() {
        return new Options();
    }

    @Bean
    @ConditionalOnMissingBean
    public Contract defaultFeignContract() {
        return new SpringContract();
    }

}
