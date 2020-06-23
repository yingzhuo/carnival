/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.shield.autoconfig;

import com.github.yingzhuo.carnival.mvc.props.AbstractWebFilterProps;
import com.github.yingzhuo.carnival.shield.algorithm.Algorithm;
import com.github.yingzhuo.carnival.shield.algorithm.Algorithms;
import com.github.yingzhuo.carnival.shield.core.ShieldFilter;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author 应卓
 * @since 1.6.21
 */
@EnableConfigurationProperties(ShieldCoreAutoConfig.Props.class)
@ConditionalOnWebApplication
@ConditionalOnProperty(prefix = "carnival.shield-filter", name = "enabled", havingValue = "true", matchIfMissing = true)
public class ShieldCoreAutoConfig {

    @Autowired
    private RequestMappingHandlerMapping mappings;

    @Autowired(required = false)
    private Algorithm algorithm;

    @Bean
    public FilterRegistrationBean<ShieldFilter> shieldFilter(Props props) {
        final FilterRegistrationBean<ShieldFilter> bean =
                new FilterRegistrationBean<>(
                        new ShieldFilter(
                                mappings,
                                algorithm != null ? algorithm : Algorithms.aes(Algorithm.class.getName()),
                                props.getCharset())
                );

        bean.setName(props.getFilterName());
        bean.setOrder(props.getOrder());
        bean.addUrlPatterns(props.getUrlPatterns());
        return bean;
    }

    // ----------------------------------------------------------------------------------------------------------------

    @Getter
    @Setter
    @ConfigurationProperties(prefix = "carnival.shield-filter")
    static class Props extends AbstractWebFilterProps {

        private boolean enabled = true;
        private Charset charset = StandardCharsets.UTF_8;

        public Props() {
            super.setOrder(0);
            super.setFilterName(ShieldFilter.class.getName());
        }

    }
}
