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

import com.github.yingzhuo.carnival.mvc.support.WebRootSettingInterceptor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 应卓
 */
@Lazy(false)
@ConditionalOnWebApplication
@ConditionalOnProperty(prefix = "carnival.mvc.webroot", name = "enabled", havingValue = "true")
@EnableConfigurationProperties(MvcWebRootAutoConfig.Props.class)
public class MvcWebRootAutoConfig implements WebMvcConfigurer {

    @Autowired
    private Props props;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new WebRootSettingInterceptor(props.getRequestAttributeName())).addPathPatterns("/", "/**");
    }

    @Getter
    @Setter
    @ConfigurationProperties("carnival.mvc.webroot")
    static class Props {
        private boolean enabled = false;
        private String requestAttributeName = "WEBROOT";
    }

}
