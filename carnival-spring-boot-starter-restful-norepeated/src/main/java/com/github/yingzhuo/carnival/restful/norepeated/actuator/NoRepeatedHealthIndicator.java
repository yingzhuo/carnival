/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.norepeated.actuator;

import com.github.yingzhuo.carnival.restful.norepeated.support.JedisCommandsFinder;
import com.github.yingzhuo.carnival.spring.SpringUtils;
import lombok.val;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;

import java.util.UUID;

/**
 * @author 应卓
 * @since 1.6.7
 */
public class NoRepeatedHealthIndicator extends AbstractHealthIndicator {

    @Override
    protected void doHealthCheck(Health.Builder builder) {
        val jedis = SpringUtils.getBean(JedisCommandsFinder.class).find();
        val uuid = UUID.randomUUID().toString();

        try {
            jedis.set(uuid, uuid);
            jedis.del(uuid);
            builder.up();
        } catch (Exception e) {
            builder.down();
        }
    }

}
