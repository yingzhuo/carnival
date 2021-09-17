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

import com.github.yingzhuo.carnival.common.io.ResourceOption;
import com.github.yingzhuo.carnival.redis.support.AbstractLockBean;
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
public class RedisLockBeanImpl extends AbstractLockBean implements RedisLockBean {

    private final StringRedisTemplate template;
    private final String lockScript = ResourceOption.of("classpath:/lua-script/lock/lock.lua").asText();
    private final String unlockScript = ResourceOption.of("classpath:/lua-script/lock/release.lua").asText();

    public RedisLockBeanImpl(RedisConnectionFactory factory) {
        this.template = new StringRedisTemplate(factory);
    }

    @Override
    public boolean lock(String key, Duration max) {
        final RedisScript<Long> script = new DefaultRedisScript<>(lockScript, Long.class);
        final Long result =
                template.execute(
                        script,
                        Collections.singletonList(generateLockKey(key)),    // KEYS[1]
                        generateLockValue(),    // ARGV[1]
                        getMaxOfDefault(max)    // ARGV[2]
                );
        return result != null && result == 1L;
    }

    @Override
    public boolean release(String key) {
        final RedisScript<Long> script = new DefaultRedisScript<>(unlockScript, Long.class);
        final Long result = template.execute(
                script,
                Collections.singletonList(generateLockKey(key)),    // KEYS[1]
                generateLockValue()     // ARGV[1]
        );
        return result != null && 1L == result;
    }

}
