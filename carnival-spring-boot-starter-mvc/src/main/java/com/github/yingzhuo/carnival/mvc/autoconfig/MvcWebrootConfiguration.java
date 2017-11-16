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

import com.github.yingzhuo.carnival.mvc.support.BasePathInterceptor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.PostConstruct;

/**
 * @author 应卓
 */
@ConditionalOnWebApplication
@EnableConfigurationProperties(MvcWebrootConfiguration.Props.class)
@ConditionalOnProperty(prefix = "carnival.mvc.webroot", name = "enabled", havingValue = "true", matchIfMissing = true)
@Slf4j
public class MvcWebrootConfiguration extends WebMvcConfigurerAdapter {

    private final Props props;

    public MvcWebrootConfiguration(Props props) {
        this.props = props;
    }

    @PostConstruct
    private void init() {
        log.debug("SpringBoot auto-config: {}", getClass().getName());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new BasePathInterceptor(props.getAttributeName())).addPathPatterns("/", "/**");
    }

    @Data
    @ConfigurationProperties("carnival.mvc.webroot")
    static class Props {
        private boolean enabled = true;
        private String attributeName = "WEBROOT";
    }

}
