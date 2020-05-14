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

import com.github.yingzhuo.carnival.redis.distributed.lock.autoconfig.DistributedLockCoreAutoConfig;
import com.github.yingzhuo.carnival.redis.distributed.lock.support.JedisCommandsFinder;
import com.github.yingzhuo.carnival.redis.distributed.lock.support.RequestIdCreator;
import com.github.yingzhuo.carnival.spring.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisCommands;

import java.util.Collections;
import java.util.Objects;

import static com.github.yingzhuo.carnival.redis.distributed.lock.Constant.*;

/**
 * Redis实现分布式锁
 *
 * @author 应卓
 */
@Slf4j
public final class DistributedLock {

    private DistributedLock() {
    }

    public static boolean lock(String key, long expireInMillis) {

        if (key == null) return false;

        final String prefix = SpringUtils.getBean(DistributedLockCoreAutoConfig.Props.class).getKeyPrefix();
        final String suffix = SpringUtils.getBean(DistributedLockCoreAutoConfig.Props.class).getKeySuffix();
        final RequestIdCreator requestIdCreator = SpringUtils.getBean(RequestIdCreator.class);
        final String effKey = prefix + key + suffix;
        final String requestId = requestIdCreator.create(SpringUtils.getSpringId(), Thread.currentThread().getId());

        if (log.isTraceEnabled()) {
            log.trace("try to lock. key = {}, value = {}", effKey, requestId);
        }

        JedisCommands jedis = SpringUtils.getBean(JedisCommandsFinder.class).find();

        String result = jedis.set(
                effKey,
                requestId,
                SET_IF_NOT_EXIST,
                SET_WITH_EXPIRE_TIME,
                expireInMillis);

        try {
            return LOCK_SUCCESS.equals(result);
        } finally {
            closeQuietly(jedis);
        }
    }

    public static boolean release(String key) {
        Assert.hasText(key, "key is null or blank.");

        final String prefix = SpringUtils.getBean(DistributedLockCoreAutoConfig.Props.class).getKeyPrefix();
        final String suffix = SpringUtils.getBean(DistributedLockCoreAutoConfig.Props.class).getKeySuffix();
        final RequestIdCreator requestIdCreator = SpringUtils.getBean(RequestIdCreator.class);
        final String effKey = prefix + key + suffix;
        final String requestId = requestIdCreator.create(SpringUtils.getSpringId(), Thread.currentThread().getId());

        if (log.isTraceEnabled()) {
            log.trace("try to release. key = {}, value = {}", effKey, requestId);
        }

        JedisCommands jedis = SpringUtils.getBean(JedisCommandsFinder.class).find();

        final String lua = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";

        final Object result;

        if (jedis instanceof Jedis) {
            result = ((Jedis) jedis).eval(lua, Collections.singletonList(effKey), Collections.singletonList(requestId));
        } else {
            result = ((JedisCluster) jedis).eval(lua, Collections.singletonList(effKey), Collections.singletonList(requestId));
        }

        try {
            return RELEASE_SUCCESS.equals(result);
        } finally {
            closeQuietly(jedis);
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

    // -----------------------------------------------------------------------------------------------------------------

    private static void closeQuietly(JedisCommands jedisCommands) {
        if (jedisCommands != null) {

            if (jedisCommands instanceof Jedis) {
                ((Jedis) jedisCommands).close();
            }
        }
    }

}
