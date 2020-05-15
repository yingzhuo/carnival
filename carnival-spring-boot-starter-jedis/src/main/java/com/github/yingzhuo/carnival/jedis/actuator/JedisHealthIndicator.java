/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.jedis.actuator;

import com.github.yingzhuo.carnival.jedis.util.JedisUtils;
import lombok.val;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;

import java.util.UUID;

/**
 * @author 应卓
 * @since 1.6.7
 */
public class JedisHealthIndicator extends AbstractHealthIndicator {

    @Override
    protected void doHealthCheck(Health.Builder builder) {
        val commands = JedisUtils.getJedisCommands();

        try {
            val uuid = UUID.randomUUID().toString();
            commands.set(uuid, "1");
            commands.del(uuid);
            builder.up();
        } catch (Exception e) {
            builder.down(e);
        } finally {
            JedisUtils.close(commands);
        }

    }

}
