/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.nonce.impl;

import com.github.yingzhuo.carnival.nonce.NonceToken;
import com.github.yingzhuo.carnival.nonce.NonceTokenDao;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.time.Duration;

/**
 * @author 应卓
 * @since 1.6.29
 */
public class RedisNonceTokenDao implements NonceTokenDao {

    private final StringRedisTemplate redisTemplate;
    private Duration tokenTimeToLive;

    public RedisNonceTokenDao(RedisConnectionFactory connectionFactory) {
        this.redisTemplate = new StringRedisTemplate(connectionFactory);
    }

    @Override
    public void save(NonceToken nonceToken) {
        String key = nonceToken.getValue();
        String value = nonceToken.getValue();
        if (tokenTimeToLive != null) {
            redisTemplate.opsForValue().set(key, value, tokenTimeToLive);
        } else {
            redisTemplate.opsForValue().set(key, value);
        }
    }

    @Override
    public void delete(NonceToken nonceToken) {
        redisTemplate.delete(nonceToken.getValue());
    }

    @Override
    public boolean exists(NonceToken nonceToken) {
        String value = redisTemplate.opsForValue().get(nonceToken.getValue());
        if (value == null) {
            return false;
        }
        return value.equals(nonceToken.getValue());
    }

    public void setTokenTimeToLive(Duration tokenTimeToLive) {
        this.tokenTimeToLive = tokenTimeToLive;
    }

}
