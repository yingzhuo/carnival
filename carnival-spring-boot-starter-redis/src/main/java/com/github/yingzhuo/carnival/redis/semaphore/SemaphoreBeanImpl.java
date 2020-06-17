/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.redis.semaphore;

import com.github.yingzhuo.carnival.common.io.ResourceText;
import com.github.yingzhuo.carnival.redis.support.AbstractLockBean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;

import java.time.Duration;
import java.util.Collections;

/**
 * @author 应卓
 * @since 1.6.19
 */
public class SemaphoreBeanImpl extends AbstractLockBean implements SemaphoreBean {

    private final StringRedisTemplate template;
    private final String lockScript = ResourceText.of("classpath:/lua-script/semaphore/lock.lua").getText();
    private final String unlockScript = ResourceText.of("classpath:/lua-script/semaphore/release.lua").getText();

    public SemaphoreBeanImpl(RedisConnectionFactory connectionFactory) {
        this.template = new StringRedisTemplate(connectionFactory);
    }

    @Override
    public boolean lock(String key, int size, Duration max) {
        final RedisScript<Long> script = new DefaultRedisScript<>(lockScript, Long.class);
        final Long result =
                template.execute(
                        script,
                        Collections.singletonList(generateLockKey(key)),    // KEYS[1]
                        generateLockValue(),    // ARGV[1]
                        String.valueOf(size),   // ARGV[2]
                        getMaxOfDefault(max)    // ARGV[3]
                );
        return result != null && result == 1L;
    }

    @Override
    public boolean release(String key) {
        final RedisScript<Long> script = new DefaultRedisScript<>(unlockScript, Long.class);
        final Long result =
                template.execute(
                        script,
                        Collections.singletonList(generateLockKey(key)),    // KEYS[1]
                        generateLockValue()    // ARGV[1]
                );
        return result != null && result == 1L;
    }

}
