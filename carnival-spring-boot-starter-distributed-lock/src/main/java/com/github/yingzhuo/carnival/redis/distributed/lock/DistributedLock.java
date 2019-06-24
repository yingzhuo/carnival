/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.redis.distributed.lock;

import com.github.yingzhuo.carnival.spring.SpringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisCommands;

import java.io.IOException;
import java.util.Collections;
import java.util.Objects;

/**
 * Redis实现分布式锁
 *
 * @author 应卓
 */
public final class DistributedLock {

    private static final Logger LOGGER = LoggerFactory.getLogger(DistributedLock.class);

    private static final String LOCK_SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "PX";
    private static final Long RELEASE_SUCCESS = 1L;

    private DistributedLock() {
        super();
    }

    public static boolean lock(String key, long expireInMillis) {

        Assert.hasText(key, "key is null or blank.");

        final RequestIdFactory requestIdFactory = SpringUtils.getBean(RequestIdFactory.class);
        final String effKey = key;
        final String requestId = requestIdFactory.create(SpringUtils.getSpringId(), Thread.currentThread().getId());

        LOGGER.trace("try to LOCK");
        LOGGER.trace("key: {}", effKey);
        LOGGER.trace("value: {}", requestId);

        JedisCommands jedisCommands = SpringUtils.getBean(JedisCommandsFinder.class).find();

        String result = jedisCommands.set(
                effKey,
                requestId,
                SET_IF_NOT_EXIST,
                SET_WITH_EXPIRE_TIME,
                expireInMillis);

        try {
            return LOCK_SUCCESS.equals(result);
        } finally {
            closeQuietly(jedisCommands);
        }
    }

    public static boolean release(String key) {
        Assert.hasText(key, "key is null or blank.");

        final RequestIdFactory requestIdFactory = SpringUtils.getBean(RequestIdFactory.class);
        final String effKey = key;
        final String requestId = requestIdFactory.create(SpringUtils.getSpringId(), Thread.currentThread().getId());

        LOGGER.trace("try to RELEASE");
        LOGGER.trace("key: {}", effKey);
        LOGGER.trace("value: {}", requestId);

        JedisCommands jedisCommands = SpringUtils.getBean(JedisCommandsFinder.class).find();

        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";

        Object result;

        if (jedisCommands instanceof Jedis) {
            result = ((Jedis) jedisCommands).eval(script, Collections.singletonList(effKey), Collections.singletonList(requestId));
        } else {
            result = ((JedisCluster) jedisCommands).eval(script, Collections.singletonList(effKey), Collections.singletonList(requestId));
        }

        try {
            return RELEASE_SUCCESS.equals(result);
        } finally {
            closeQuietly(jedisCommands);
        }
    }

    public static boolean lock(short key, long expireInMillis) {
        return lock(String.valueOf(key), expireInMillis);
    }

    public static boolean release(short key) {
        return release(String.valueOf(key));
    }

    public static boolean lock(long key, long expireInMillis) {
        return lock(String.valueOf(key), expireInMillis);
    }

    public static boolean release(long key) {
        return release(String.valueOf(key));
    }

    public static boolean lock(int key, long expireInMillis) {
        return lock(String.valueOf(key), expireInMillis);
    }

    public static boolean release(int key) {
        return release(String.valueOf(key));
    }

    public static boolean lock(double key, long expireInMillis) {
        return lock(String.valueOf(key), expireInMillis);
    }

    public static boolean release(double key) {
        return release(String.valueOf(key));
    }

    public static void waitAndRun(String key, long expireInMillis, Runnable runnable) {
        Objects.requireNonNull(runnable);

        while (true) {
            if (lock(key, expireInMillis)) {
                runnable.run();
                release(key);
                return;
            }
        }
    }

    public static void waitAndRun(short key, long expireInMillis, Runnable runnable) {
        waitAndRun(String.valueOf(key), expireInMillis, runnable);
    }

    public static void waitAndRun(int key, long expireInMillis, Runnable runnable) {
        waitAndRun(String.valueOf(key), expireInMillis, runnable);
    }

    public static void waitAndRun(long key, long expireInMillis, Runnable runnable) {
        waitAndRun(String.valueOf(key), expireInMillis, runnable);
    }

    public static void waitAndRun(double key, long expireInMillis, Runnable runnable) {
        waitAndRun(String.valueOf(key), expireInMillis, runnable);
    }

    private static void closeQuietly(JedisCommands jedisCommands) {
        if (jedisCommands != null) {

            if (jedisCommands instanceof Jedis) {
                ((Jedis) jedisCommands).close();
            }

            if (jedisCommands instanceof JedisCluster) {
                try {
                    ((JedisCluster) jedisCommands).close();
                } catch (IOException e) {
                    // NOP
                }
            }
        }
    }
}
