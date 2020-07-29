/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.security.responsebody.autoconfig;

import com.github.yingzhuo.carnival.restful.security.responsebody.ResponseBodyEncryptingAlgorithm;
import com.github.yingzhuo.carnival.restful.security.responsebody.core.ResponseBodyEncryptingFilter;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

/**
 * @author 应卓
 * @since 1.6.30
 */
@ConditionalOnWebApplication
@ConditionalOnProperty(prefix = "carnival.security.response-body-encrypting", name = "enabled", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(ResponseBodyEncryptingAutoConfig.Props.class)
public class ResponseBodyEncryptingAutoConfig {

    @Autowired(required = false)
    private ResponseBodyEncryptingAlgorithm algorithm;

    @Bean
    public FilterRegistrationBean<ResponseBodyEncryptingFilter> responseBodyEncryptingFilter(Props props) {
        final ResponseBodyEncryptingFilter filter = new ResponseBodyEncryptingFilter();
        filter.setCharset(props.getEncryptingCharset());
        filter.setAlgorithm(algorithm != null ? algorithm : ResponseBodyEncryptingAlgorithm.getDefault());
        filter.setDebugMode(props.isDebugMode());
        filter.setExcludeAntPatterns(props.getFilter().getExcludeAntPatterns());
        filter.init();

        final FilterRegistrationBean<ResponseBodyEncryptingFilter> bean = new FilterRegistrationBean<>(filter);
        bean.setName(ResponseBodyEncryptingFilter.class.getName());
        bean.setOrder(props.getFilter().getOrder());
        bean.addUrlPatterns("/*");
        return bean;
    }

    // ----------------------------------------------------------------------------------------------------------------

    @Getter
    @Setter
    @ConfigurationProperties(prefix = "carnival.security.response-body-encrypting")
    static class Props {
        private boolean enabled = true;
        private boolean debugMode = false;
        private Charset encryptingCharset = StandardCharsets.UTF_8;
        private Filter filter = new Filter();
    }

    // ----------------------------------------------------------------------------------------------------------------

    @Getter
    @Setter
    static class Filter {
        private int order = Ordered.LOWEST_PRECEDENCE;
        private Set<String> excludeAntPatterns = new HashSet<>();
    }

}
