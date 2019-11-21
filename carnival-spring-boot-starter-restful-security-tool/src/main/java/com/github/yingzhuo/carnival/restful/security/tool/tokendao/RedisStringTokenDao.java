/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.security.tool.tokendao;

import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @author 应卓
 * @since 1.2.5
 */
public class RedisStringTokenDao implements StringTokenDao {

    private final StringRedisTemplate stringRedisTemplate;
    private final long timeout;
    private final TimeUnit timeUnit;

    public RedisStringTokenDao(StringRedisTemplate stringRedisTemplate) {
        this(stringRedisTemplate, 0, TimeUnit.DAYS);
    }

    public RedisStringTokenDao(StringRedisTemplate stringRedisTemplate, long timeout, TimeUnit timeUnit) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.timeout = timeout;
        this.timeUnit = timeUnit;
    }

    @Override
    public void save(String key, String token) {
        if (this.timeout >= 0) {
            stringRedisTemplate.opsForValue().set(key, token, this.timeout, this.timeUnit);
        } else {
            stringRedisTemplate.opsForValue().set(key, token);
        }
    }

    @Override
    public String find(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

}
