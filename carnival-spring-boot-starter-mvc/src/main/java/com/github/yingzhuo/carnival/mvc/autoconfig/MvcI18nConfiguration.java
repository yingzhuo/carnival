/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.mvc.autoconfig;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import javax.annotation.PostConstruct;

@ConditionalOnWebApplication
@ConditionalOnProperty(prefix = "carnival.mvc.i18n", name = "enabled", havingValue = "true")
@EnableConfigurationProperties(MvcI18nConfiguration.MvcI18nProps.class)
@Slf4j
public class MvcI18nConfiguration extends WebMvcConfigurerAdapter {

    private final MvcI18nProps props;

    public MvcI18nConfiguration(MvcI18nProps props) {
        this.props = props;
    }

    @PostConstruct
    private void init() {
        log.debug("SpringBoot auto-config: {}", getClass().getName());
    }

    @Bean
    @ConditionalOnMissingBean(LocaleResolver.class)
    public LocaleResolver localeResolver() {
        return new CookieLocaleResolver();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        interceptor.setParamName(props.getParamName());
        interceptor.setIgnoreInvalidLocale(props.isIgnoreInvalidLocale());
        registry.addInterceptor(interceptor).addPathPatterns("/", "/**");
    }

    @Data
    @ConfigurationProperties(prefix = "carnival.mvc.i18n")
    static class MvcI18nProps {
        private boolean enabled = true;
        private String paramName = "locale";
        private boolean ignoreInvalidLocale = true;
    }

}
