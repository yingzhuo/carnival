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

import com.github.yingzhuo.carnival.common.io.ResourceText;
import com.github.yingzhuo.carnival.spring.SpringUtils;
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
public class RedisLockBeanImpl implements RedisLockBean, InitializingBean {

    private final StringRedisTemplate template;
    private String lockScript = ResourceText.of("classpath:/lua-script/lock/lock.lua").getText();
    private String unlockScript = ResourceText.of("classpath:/lua-script/lock/unlock.lua").getText();
    private String prefix = "";
    private String suffix = "";
    private Duration defaultMax;

    public RedisLockBeanImpl(RedisConnectionFactory factory) {
        this.template = new StringRedisTemplate(factory);
    }

    private String generateLockKey(String key) {
        return prefix + key + suffix;
    }

    private String generateLockValue() {
        return SpringUtils.getSpringId() + "." + Thread.currentThread().getId();
    }

    private String getMaxOfDefault(Duration ttl) {
        if (ttl == null) {
            return defaultMax.getSeconds() + "";
        } else {
            return ttl.getSeconds() + "";
        }
    }

    @Override
    public void afterPropertiesSet() {
        if (prefix == null) prefix = "";
        if (suffix == null) suffix = "";
        if (defaultMax == null) defaultMax = Duration.ofSeconds(10);
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public void setDefaultMax(Duration defaultMax) {
        this.defaultMax = defaultMax;
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
