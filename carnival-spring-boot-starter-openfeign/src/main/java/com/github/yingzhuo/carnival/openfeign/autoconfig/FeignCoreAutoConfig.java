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

import com.github.yingzhuo.carnival.openfeign.AnnotatedParameterProcessor;
import com.github.yingzhuo.carnival.openfeign.FeignFormatterRegistrar;
import com.github.yingzhuo.carnival.openfeign.props.FeignProperties;
import com.github.yingzhuo.carnival.openfeign.support.SpringContract;
import feign.Contract;
import feign.Retryer;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.format.support.DefaultFormattingConversionService;

import java.util.ArrayList;
import java.util.List;

import static feign.Request.Options;

/**
 * @author 应卓
 * @since 1.6.16
 */
@Slf4j
@Lazy(false)
@EnableConfigurationProperties(FeignProperties.class)
public class FeignCoreAutoConfig {

    @Autowired(required = false)
    private List<FeignFormatterRegistrar> feignFormatterRegistrars = new ArrayList<>();

    @Autowired(required = false)
    private List<AnnotatedParameterProcessor> annotatedParameterProcessors = new ArrayList<>();

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
        return Retryer.NEVER_RETRY;
    }

    @Bean
    @ConditionalOnMissingBean
    public Options defaultFeignOptions() {
        return new Options();
    }

    @Bean
    @ConditionalOnMissingBean
    public Contract defaultFeignContract() {
        try {
            final DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService();
            for (FeignFormatterRegistrar feignFormatterRegistrar : this.feignFormatterRegistrars) {
                feignFormatterRegistrar.registerFormatters(conversionService);
            }
            return new SpringContract(annotatedParameterProcessors, conversionService);
        } catch (Exception e) {
            log.error(e.getMessage(), e);   // 不要用lambda表达式
            return new SpringContract(annotatedParameterProcessors);
        }
    }

}
