/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.actuator.autoconfig;

import com.github.yingzhuo.carnival.actuator.endpoint.DescriptionEndpoint;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;

import java.io.Serializable;

/**
 * @author 应卓
 * @since 1.6.4
 */
@EnableConfigurationProperties(ActuatorCoreConfig.Props.class)
@ConditionalOnProperty(prefix = "carnival.actuator", name = "enabled", havingValue = "true", matchIfMissing = true)
public class ActuatorCoreConfig {

    @Bean
    @ConditionalOnProperty(prefix = "carnival.actuator.description", name = "enabled", havingValue = "true", matchIfMissing = true)
    public DescriptionEndpoint descriptionEndpoint(Props props) {
        return new DescriptionEndpoint(props.getDescription().getHtml());
    }

    // -----------------------------------------------------------------------------------------------------------------

    @Getter
    @Setter
    @ConfigurationProperties(prefix = "carnival.actuator")
    static class Props implements Serializable {
        private boolean enabled = true;
        private Description description = new Description();
    }

    @Getter
    @Setter
    static class Description implements Serializable {
        private boolean enabled = true;
        private Resource html;
    }

}
