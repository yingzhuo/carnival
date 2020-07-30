/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.security.params.autoconfig;

import com.github.yingzhuo.carnival.restful.security.params.ParamsValidatingAlgorithm;
import com.github.yingzhuo.carnival.restful.security.params.core.ParamsValidatingInterceptor;
import lombok.Getter;
import lombok.Setter;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.convert.DurationUnit;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * @author 应卓
 * @since 1.6.30
 */
@ConditionalOnWebApplication
@ConditionalOnProperty(prefix = "carnival.security.params-validating", name = "enabled", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(RestfulSecurityParamsAutoConfig.Props.class)
public class RestfulSecurityParamsAutoConfig implements WebMvcConfigurer {

    @Autowired
    private Props props;

    @Autowired(required = false)
    private ParamsValidatingAlgorithm algorithm;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        val interceptor = new ParamsValidatingInterceptor();
        interceptor.setDebugMode(props.isTroubleshooting());
        interceptor.setExcludeAntPatterns(props.getInterceptor().getExcludeAntPatterns());
        Optional.ofNullable(algorithm).ifPresent(interceptor::setAlgorithm);
        Optional.ofNullable(props.getInterceptor().getExcludeAntPatterns()).ifPresent(interceptor::setExcludeAntPatterns);
        Optional.ofNullable(props.getResolver().getSignParameterName()).ifPresent(interceptor::setSignParameterName);
        Optional.ofNullable(props.getResolver().getNonceParameterName()).ifPresent(interceptor::setNonceParameterName);
        Optional.ofNullable(props.getResolver().getTimestampParameterName()).ifPresent(interceptor::setTimestampParameterName);
        Optional.ofNullable(props.getMaxAllowedTimestampDiff()).ifPresent(interceptor::setMaxAllowedTimestampDiff);
        Optional.ofNullable(props.getResolver().getSignHeaderName()).ifPresent(interceptor::setSignHeaderName);
        Optional.ofNullable(props.getResolver().getNonceHeaderName()).ifPresent(interceptor::setNonceHeaderName);
        Optional.ofNullable(props.getResolver().getTimestampHeaderName()).ifPresent(interceptor::setTimestampHeaderName);
        registry.addInterceptor(interceptor).order(props.getInterceptor().getOrder());
    }

    // ----------------------------------------------------------------------------------------------------------------

    @Getter
    @Setter
    @ConfigurationProperties(prefix = "carnival.security.params-validating")
    static class Props {
        private boolean enabled = true;
        private boolean troubleshooting = false;
        private Interceptor interceptor = new Interceptor();
        private Resolver resolver = new Resolver();
        @DurationUnit(ChronoUnit.MINUTES)
        private Duration maxAllowedTimestampDiff = null;
    }

    // ----------------------------------------------------------------------------------------------------------------

    @Getter
    @Setter
    static class Interceptor {
        private int order = Ordered.LOWEST_PRECEDENCE;
        private Set<String> excludeAntPatterns = new HashSet<>();
    }

    // ----------------------------------------------------------------------------------------------------------------

    @Getter
    @Setter
    static class Resolver {
        private String nonceParameterName = "_nonce";
        private String timestampParameterName = "_timestamp";
        private String signParameterName = "_sign";
        private String nonceHeaderName = null;
        private String timestampHeaderName = null;
        private String signHeaderName = null;
    }
}
