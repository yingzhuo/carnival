/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.etcd;

import lombok.val;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

import java.util.UUID;

/**
 * @author 应卓
 * @since 1.6.11
 */
@ConditionalOnClass(name = "org.springframework.boot.actuate.endpoint.annotation.Endpoint")
@ConditionalOnProperty(prefix = "carnival.etcd", name = "enabled", havingValue = "true", matchIfMissing = true)
class ETCDActuatorAutoConfig {

    @Bean
    public ETCDHealthIndicator etcdHealthIndicator() {
        return new ETCDHealthIndicator();
    }

    private static class ETCDHealthIndicator extends AbstractHealthIndicator {

        @Override
        protected void doHealthCheck(Health.Builder builder) {
            val uuid = UUID.randomUUID().toString();
            try {
                ETCD.get(uuid);
                builder.up();
            } catch (Exception e) {
                builder.down();
            }
        }
    }

}
