/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.norepeated.autoconfig;

import com.github.yingzhuo.carnival.restful.norepeated.actuator.NoRepeatedHealthIndicator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;

/**
 * @author 应卓
 * @since 1.6.7
 */
@Lazy(false)
@ConditionalOnProperty(prefix = "carnival.no-repeated", name = "enabled", havingValue = "true", matchIfMissing = true)
@ConditionalOnClass(name = "org.springframework.boot.actuate.endpoint.annotation.Endpoint")
@ConditionalOnWebApplication
public class NoRepeatedActuatorAutoConfig {

    @Bean
    public NoRepeatedHealthIndicator noRepeatedHealthIndicator() {
        return new NoRepeatedHealthIndicator();
    }

}
