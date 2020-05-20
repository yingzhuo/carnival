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
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.time.Duration;

/**
 * @author 应卓
 * @since 1.6.8
 */
public class RedisCaptchaDao implements CaptchaDao, InitializingBean {

    private Duration timeToLive;
    private String keyPrefix = "carnival-captcha-";
    private String keySuffix = "";
    private StringRedisTemplate template;

    public RedisCaptchaDao() {
    }

    public RedisCaptchaDao(RedisConnectionFactory connectionFactory, Duration timeToLive, String keyPrefix, String keySuffix) {
        this.template = new StringRedisTemplate(connectionFactory);
        this.timeToLive = timeToLive;
        this.keyPrefix = keyPrefix;
        this.keySuffix = keySuffix;
    }

    @Override
    public void afterPropertiesSet() {
        if (timeToLive == null) {
            timeToLive = Duration.ofMinutes(5);
        }

        if (keyPrefix == null) {
            keyPrefix = "";
        }

        if (keySuffix == null) {
            keySuffix = "";
        }
    }

    @Override
    public void save(String accessKey, String captcha) {
        final String redisKey = genRedisKey(accessKey);
        template.opsForValue().set(redisKey, captcha, timeToLive);
    }

    @Override
    public String load(String accessKey) {
        final String redisKey = genRedisKey(accessKey);
        return template.opsForValue().get(redisKey);
    }

    @Override
    public void delete(String accessKey) {
        final String redisKey = genRedisKey(accessKey);
        template.delete(redisKey);
    }

    private String genRedisKey(String accessKey) {
        return keyPrefix + accessKey + keySuffix;
    }

    public void setTimeToLive(Duration timeToLive) {
        this.timeToLive = timeToLive;
    }

    public void setKeyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }

    public void setKeySuffix(String keySuffix) {
        this.keySuffix = keySuffix;
    }

    public void setTemplate(StringRedisTemplate template) {
        this.template = template;
    }

}
