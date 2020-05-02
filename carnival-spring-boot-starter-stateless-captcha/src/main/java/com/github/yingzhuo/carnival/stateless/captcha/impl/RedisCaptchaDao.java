/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.stateless.captcha.impl;

import com.github.yingzhuo.carnival.stateless.captcha.CaptchaDao;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.time.Duration;
import java.util.Objects;
import java.util.Optional;

/**
 * @author 应卓
 * @since 1.5.3
 */
@Deprecated
public class RedisCaptchaDao implements CaptchaDao {

    private static final Duration DEFAULT_TTL = Duration.ofMinutes(10L);

    private final StringRedisTemplate redisTemplate;
    private final Duration ttl;

    public RedisCaptchaDao(StringRedisTemplate redisTemplate) {
        this(redisTemplate, null);
    }

    public RedisCaptchaDao(StringRedisTemplate redisTemplate, Duration ttl) {
        this.redisTemplate = redisTemplate;
        this.ttl = ttl != null ? ttl : DEFAULT_TTL;
    }

    @Override
    public void save(String captchaId, String captchaValue) {
        final String key = createRedisKey(captchaId);
        redisTemplate.opsForValue().set(key, captchaValue, ttl);
    }

    @Override
    public Optional<String> load(String captchaId) {
        return Optional.ofNullable(redisTemplate.opsForValue().get(createRedisKey(captchaId)));
    }

    @Override
    public void delete(String captchaId) {
        redisTemplate.delete(createRedisKey(captchaId));
    }

    private String createRedisKey(String captchaId) {
        return "carnival.stateless.captcha." + Objects.requireNonNull(captchaId);
    }

}
