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

import com.github.yingzhuo.carnival.mvc.download.DownloadHandlerMethodReturnValueHandler;
import com.github.yingzhuo.carnival.mvc.support.IpHandlerMethodArgumentResolver;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.Ordered;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.BeanNameViewResolver;
import org.springframework.web.util.UrlPathHelper;

import java.util.List;
import java.util.Optional;

@ConditionalOnWebApplication
@EnableConfigurationProperties(MvcEnhancementAutoConfig.Props.class)
@ConditionalOnProperty(prefix = "carnival.mvc.enhancement", name = "enabled", havingValue = "true", matchIfMissing = true)
public class MvcEnhancementAutoConfig implements WebMvcConfigurer {

    @Autowired(required = false)
    public void config(BeanNameViewResolver resolver) {
        if (resolver != null) {
            resolver.setOrder(Ordered.HIGHEST_PRECEDENCE);
        }
    }

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        final UrlPathHelper helper = Optional.ofNullable(configurer.getUrlPathHelper()).orElseGet(UrlPathHelper::new);
        helper.setDefaultEncoding("UTF-8");
        helper.setRemoveSemicolonContent(false);
        configurer.setUrlPathHelper(helper);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new IpHandlerMethodArgumentResolver());
    }

    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> handlers) {
        handlers.add(new DownloadHandlerMethodReturnValueHandler());
    }

    @Getter
    @Setter
    @ConfigurationProperties("carnival.mvc.enhancement")
    static class Props {
        private boolean enabled = true;
    }

}
