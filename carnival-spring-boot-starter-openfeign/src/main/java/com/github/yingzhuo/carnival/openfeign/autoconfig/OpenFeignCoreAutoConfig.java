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

import com.github.yingzhuo.carnival.openfeign.props.OpenFeignProps;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.spring.SpringContract;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;

/**
 * @author 应卓
 * @since 1.6.16
 */
@Lazy(false)
@EnableConfigurationProperties(OpenFeignProps.class)
@ConditionalOnProperty(prefix = "carnival.openfeign", name = "enabled", havingValue = "true", matchIfMissing = true)
public class OpenFeignCoreAutoConfig {

    @Bean
    @ConditionalOnMissingBean
    public Encoder openFeignEncoder() {
        return new JacksonEncoder();
    }

    @Bean
    @ConditionalOnMissingBean
    public Decoder openFeignDecoder() {
        return new JacksonDecoder();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "carnival.openfeign", name = "annotation-style", havingValue = "spring", matchIfMissing = true)
    public SpringContract springContract() {
        return new SpringContract();
    }

}
