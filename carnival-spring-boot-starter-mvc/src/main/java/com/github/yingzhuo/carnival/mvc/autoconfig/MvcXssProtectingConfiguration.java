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

import com.github.yingzhuo.carnival.mvc.support.XssProtectingRequest;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 应卓
 */
@Slf4j
@ConditionalOnWebApplication
@ConditionalOnProperty(prefix = "carnival.mvc.xss-protecting", name = "enabled", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(MvcXssProtectingConfiguration.Props.class)
public class MvcXssProtectingConfiguration implements WebMvcConfigurer {

    @PostConstruct
    private void init() {
        log.debug("SpringBoot auto-config: {}", getClass().getName());
    }

    @Bean
    public FilterRegistrationBean xssProtectingFilter() {
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new XssProtectingFilter());
        bean.setName(XssProtectingFilter.class.getSimpleName());
        bean.addUrlPatterns("/", "/**");
        bean.setOrder(0);
        return bean;
    }

    @Data
    @ConfigurationProperties("carnival.mvc.xss-protecting")
    static class Props {
        private boolean enabled = true;
    }

    public static class XssProtectingFilter extends OncePerRequestFilter {
        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
            filterChain.doFilter(new XssProtectingRequest(request), response);
        }
    }

}
