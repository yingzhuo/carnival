/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.norepeated.factory;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.time.Duration;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author 应卓
 * @since 1.6.7
 */
public class NoRepeatedTokenFactoryImpl implements NoRepeatedTokenFactory {

    private final Duration ttl;
    private final String prefix;
    private final String suffix;
    private final StringRedisTemplate template;

    public NoRepeatedTokenFactoryImpl(RedisConnectionFactory connectionFactory, Duration ttl, String prefix, String suffix) {
        this.template = new StringRedisTemplate(connectionFactory);
        this.ttl = ttl != null ? ttl : Duration.ofMinutes(5L);
        this.prefix = prefix != null ? prefix : "";
        this.suffix = suffix != null ? suffix : "";
    }

    @Override
    public String create() {
        final String uuid = UUID.randomUUID().toString();
        final String redisKey = createRedisKey(uuid);

        template.opsForValue().increment(redisKey);
        template.expire(redisKey, ttl.getSeconds(), TimeUnit.SECONDS);
        return redisKey;
    }

    private String createRedisKey(String key) {
        return prefix + key + suffix;
    }

    public Duration getTtl() {
        return ttl;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getSuffix() {
        return suffix;
    }

}
