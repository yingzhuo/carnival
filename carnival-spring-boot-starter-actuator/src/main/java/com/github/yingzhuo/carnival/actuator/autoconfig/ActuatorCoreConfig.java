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

import com.github.yingzhuo.carnival.actuator.stateprobe.LivenessStateEndpoint;
import com.github.yingzhuo.carnival.actuator.stateprobe.ReadinessStateEndpoint;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * @author 应卓
 * @since 1.6.5
 */
@EnableConfigurationProperties(ActuatorCoreConfig.Props.class)
@ConditionalOnProperty(prefix = "carnival.actuator", name = "enabled", havingValue = "true", matchIfMissing = true)
public class ActuatorCoreConfig {

    @Bean
    public LivenessStateEndpoint livenessStateEndpoint() {
        return new LivenessStateEndpoint();
    }

    @Bean
    public ReadinessStateEndpoint readinessStateEndpoint() {
        return new ReadinessStateEndpoint();
    }

    @Getter
    @Setter
    @ConfigurationProperties(prefix = "carnival.actuator")
    static class Props {
        private boolean enabled = true;
    }

}
