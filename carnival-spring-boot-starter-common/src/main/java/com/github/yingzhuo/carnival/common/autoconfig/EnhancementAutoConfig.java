/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.common.autoconfig;

import com.github.yingzhuo.carnival.common.datamodel.HostAndPortConverter;
import lombok.val;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;

/**
 * @author 应卓
 * @since 1.1.9
 */
public class EnhancementAutoConfig {

    @Bean
    @ConditionalOnMissingBean
    public RestTemplate restTemplate() {
        val bean = new RestTemplate();
        val converters = bean.getMessageConverters();
        for (HttpMessageConverter<?> converter : converters) {
            if (converter instanceof StringHttpMessageConverter) {
                val c = (StringHttpMessageConverter) converter;
                c.setDefaultCharset(StandardCharsets.UTF_8);
            }
        }
        return bean;
    }

    @Bean
    @ConditionalOnMissingBean
    @ConfigurationPropertiesBinding
    public HostAndPortConverter hostAndPortConverter() {
        return new HostAndPortConverter();
    }

}
