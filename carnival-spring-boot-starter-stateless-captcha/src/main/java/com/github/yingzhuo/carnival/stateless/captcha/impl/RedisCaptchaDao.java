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
import com.github.yingzhuo.carnival.stateless.captcha.CaptchaId;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @author 应卓
 */
public class RedisCaptchaDao implements CaptchaDao {

    private final StringRedisTemplate redisTemplate;
    private String redisKeyPrefix = RedisCaptchaDao.class.getName() + "-";
    private TimeUnit timeoutTimeUnit = TimeUnit.MINUTES;
    private int timeout = 5;

    public RedisCaptchaDao(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    private String getKey(CaptchaId id) {
        return this.redisKeyPrefix + id.getId();
    }

    @Override
    public void save(CaptchaId id, String value) {
        redisTemplate.opsForValue().set(getKey(id), value, timeout, timeoutTimeUnit);
    }

    @Override
    public String load(CaptchaId id) {
        return redisTemplate.opsForValue().get(getKey(id));
    }

    @Override
    public void delete(CaptchaId id) {
        redisTemplate.delete(getKey(id));
    }

    public void setRedisKeyPrefix(String redisKeyPrefix) {
        this.redisKeyPrefix = redisKeyPrefix;
    }

    public void setTimeoutTimeUnit(TimeUnit timeoutTimeUnit) {
        this.timeoutTimeUnit = timeoutTimeUnit;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

}
