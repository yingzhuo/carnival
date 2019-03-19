/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.apigateway.autoconfig;

import com.github.yingzhuo.carnival.apigateway.filter.ContentTypeHttpHeaderSetterFilter;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * @author 应卓
 */
@Deprecated
@EnableConfigurationProperties(ContentTypeHttpHeaderSetterFilterAutoConfig.Props.class)
@ConditionalOnProperty(prefix = "carnival.api-gateway.content-type", name = "enabled", havingValue = "true")
public class ContentTypeHttpHeaderSetterFilterAutoConfig {

    @Bean
    @ConditionalOnMissingBean
    public ContentTypeHttpHeaderSetterFilter contentTypeHttpHeaderSetterFilter() {
        return new ContentTypeHttpHeaderSetterFilter();
    }

    @Getter
    @Setter
    @ConfigurationProperties(prefix = "carnival.api-gateway.content-type")
    @Deprecated
    static final class Props {
        private boolean enabled = false;
    }

}
