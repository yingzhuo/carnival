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

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 应卓
 * @since 1.1.8
 */
@Deprecated
@ConditionalOnWebApplication
@ConditionalOnProperty(prefix = "carnival.mvc.kubernetes", name = "enabled", havingValue = "true")
@EnableConfigurationProperties(MvcKubernetesAutoConfig.Props.class)
public class MvcKubernetesAutoConfig implements WebMvcConfigurer {

    @Autowired
    private Props props;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        if (StringUtils.equals(props.getLivenessProbePath(), props.getReadinessProbePath())) {
            registry.addStatusController(props.getLivenessProbePath(), HttpStatus.OK);
        } else {
            registry.addStatusController(props.getLivenessProbePath(), HttpStatus.OK);
            registry.addStatusController(props.getReadinessProbePath(), HttpStatus.OK);
        }
    }

    @Getter
    @Setter
    @ConfigurationProperties(prefix = "carnival.mvc.kubernetes")
    @Deprecated
    static class Props {
        private boolean enabled = false;
        private String livenessProbePath = "/liveness-probe";
        private String readinessProbePath = "/readiness-probe";
    }

}
