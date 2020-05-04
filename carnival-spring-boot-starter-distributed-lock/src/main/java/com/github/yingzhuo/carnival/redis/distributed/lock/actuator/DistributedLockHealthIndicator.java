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

import java.util.UUID;

/**
 * @author 应卓
 * @since 1.4.2
 */
public class DistributedLockHealthIndicator extends AbstractHealthIndicator {

    @Override
    protected void doHealthCheck(Health.Builder builder) {
        final String key = UUID.randomUUID().toString();

        try {
            if (DistributedLock.lock(key, 200)) {
                builder.up();
            } else {
                builder.down();
            }
        } catch (Exception e) {
            builder.down(e);
        } finally {
            DistributedLock.release(key);
        }
    }

}
