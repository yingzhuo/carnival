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
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author 应卓
 * @since 1.6.5
 */
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class ActuatorStateConfig {

    @Bean
    public LivenessStateEndpoint livenessStateEndpoint() {
        return new LivenessStateEndpoint();
    }

    @Bean
    public ReadinessStateEndpoint readinessStateEndpoint() {
        return new ReadinessStateEndpoint();
    }

}
