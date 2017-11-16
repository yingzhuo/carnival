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

import com.github.yingzhuo.carnival.mvc.support.IpHandlerMethodArgumentResolver;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.util.UrlPathHelper;

import javax.annotation.PostConstruct;
import java.util.List;

@ConditionalOnWebApplication
@EnableConfigurationProperties(MvcEnhancementConfiguration.Props.class)
@ConditionalOnProperty(prefix = "carnival.mvc.enhancement", name = "enabled", havingValue = "true", matchIfMissing = true)
@Slf4j
public class MvcEnhancementConfiguration extends WebMvcConfigurerAdapter {

    @PostConstruct
    private void init() {
        log.debug("SpringBoot auto-config: {}", getClass().getName());
    }

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        UrlPathHelper helper = configurer.getUrlPathHelper();
        if (helper == null) {
            helper = new UrlPathHelper();
        }
        helper.setDefaultEncoding("UTF-8");
        helper.setRemoveSemicolonContent(false);
        configurer.setUrlPathHelper(helper);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new IpHandlerMethodArgumentResolver());
    }

    @Data
    @ConfigurationProperties("carnival.mvc.enhancement")
    static class Props {
        private boolean enabled = true;
        private String attributeName = "WEBROOT";
    }

}
