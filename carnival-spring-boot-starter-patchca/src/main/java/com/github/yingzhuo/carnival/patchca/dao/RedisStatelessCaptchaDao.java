/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.patchca.dao;

import com.github.yingzhuo.carnival.patchca.CaptchaDao;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.time.Duration;
import java.util.Objects;

/**
 * @author 应卓
 * @since 1.6.2
 */
public class RedisStatelessCaptchaDao implements CaptchaDao {

    private static final String DEFAULT_REDIS_KEY_PREFIX = RedisStatelessCaptchaDao.class.getName() + "_";

    private final StringRedisTemplate redisTemplate;
    private final Duration ttl;
    private String redisKeyPrefix = DEFAULT_REDIS_KEY_PREFIX;

    public RedisStatelessCaptchaDao(StringRedisTemplate redisTemplate) {
        this(redisTemplate, null);
    }

    public RedisStatelessCaptchaDao(StringRedisTemplate redisTemplate, Duration ttl) {
        this.redisTemplate = Objects.requireNonNull(redisTemplate);
        this.ttl = ttl != null ? ttl : Duration.ofMinutes(10);
    }

    @Override
    public void save(String accessKey, String patchca) {
        redisTemplate.opsForValue().set(genRedisKey(accessKey), patchca, ttl);
    }

    @Override
    public void delete(String accessKey) {
        redisTemplate.delete(genRedisKey(accessKey));
    }

    @Override
    public String load(String accessKey) {
        return redisTemplate.opsForValue().get(genRedisKey(accessKey));
    }

    private String genRedisKey(String accessKey) {
        return redisKeyPrefix + accessKey;
    }

}
