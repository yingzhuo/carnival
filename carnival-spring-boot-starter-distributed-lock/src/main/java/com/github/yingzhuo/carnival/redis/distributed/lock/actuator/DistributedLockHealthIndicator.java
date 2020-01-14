/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.redis.distributed.lock.actuator;

import com.github.yingzhuo.carnival.redis.distributed.lock.DistributedLock;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;

import java.util.UUID;

/**
 * @author 应卓
 * @since 1.4.2
 */
public class DistributedLockHealthIndicator extends AbstractHealthIndicator implements HealthIndicator {

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {

        final String randomKey = UUID.randomUUID().toString();

        try {
            if (DistributedLock.lock(randomKey, 100)) {
                builder
                        .status(Status.UP)
                        .withDetail("distributed-lock", "Available");
            } else {
                builder
                        .status(Status.DOWN)
                        .withDetail("distributed-lock", "Not Available");
            }

        } catch (Exception e) {
            DistributedLock.release(randomKey);
        }
    }

}
