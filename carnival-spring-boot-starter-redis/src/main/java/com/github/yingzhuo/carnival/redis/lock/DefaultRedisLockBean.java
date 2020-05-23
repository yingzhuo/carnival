/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.redis.lock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;

import java.time.Duration;
import java.util.Collections;

/**
 * @author 应卓
 * @since 1.6.9
 */
@Slf4j
public class DefaultRedisLockBean extends AbstractRedisLockBean implements RedisLockBean, InitializingBean {

    private final StringRedisTemplate template;
    private Duration defaultMax;

    public DefaultRedisLockBean(RedisConnectionFactory connectionFactory) {
        super();
        this.template = new StringRedisTemplate(connectionFactory);
    }

    @Override
    public boolean lock(String key, Duration max) {
        final Duration ttl = max != null ? max : defaultMax;
        final Boolean result = template.opsForValue().setIfAbsent(genRedisKey(key), genRedisValue(), ttl);
        return result != null ? result : false;
    }

    @Override
    public boolean release(String key) {
        final String lua = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        final RedisScript<Long> script = new DefaultRedisScript<>(lua, Long.class);
        final Long result = template.execute(script, Collections.singletonList(genRedisKey(key)), genRedisValue());
        return result != null && 1L == result;
    }

    @Override
    public void afterPropertiesSet() {
        super.afterPropertiesSet();

        if (defaultMax == null) {
            defaultMax = Duration.ofSeconds(10L);
        }
    }

    public void setDefaultMax(Duration defaultMax) {
        this.defaultMax = defaultMax;
    }

}
