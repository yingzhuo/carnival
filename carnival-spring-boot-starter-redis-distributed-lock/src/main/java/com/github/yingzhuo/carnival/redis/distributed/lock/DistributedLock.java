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

import com.github.yingzhuo.carnival.redis.distributed.lock.autoconfig.DistributedLockAutoCnf;
import com.github.yingzhuo.carnival.redis.distributed.lock.request.RequestIdFactory;
import com.github.yingzhuo.carnival.spring.SpringUtils;
import org.springframework.util.Assert;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Collections;
import java.util.Objects;

/**
 * Redis实现分布式锁
 *
 * @author 应卓
 */
public final class DistributedLock {

    private static final String LOCK_SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "PX";
    private static final Long RELEASE_SUCCESS = 1L;

    private DistributedLock() {
        super();
    }

    public static boolean lock(String key, long expireInMillis) {

        Assert.hasText(key, "key is null or blank");

        final JedisPool jedisPool = SpringUtils.getBean(JedisPool.class);
        final DistributedLockAutoCnf.DistributedLockProps props = SpringUtils.getBean(DistributedLockAutoCnf.DistributedLockProps.class);
        final RequestIdFactory requestIdFactory = SpringUtils.getBean(RequestIdFactory.class);
        final String effKey = props.getKeyScope() + key;
        final String requestId = requestIdFactory.create(SpringUtils.getSpringId(), Thread.currentThread().getId());

        try (Jedis jedis = jedisPool.getResource()) {
            String result = jedis.set(
                    effKey,
                    requestId,
                    SET_IF_NOT_EXIST,
                    SET_WITH_EXPIRE_TIME,
                    expireInMillis);

            return LOCK_SUCCESS.equals(result);
        }
    }

    public static boolean release(String key) {

        Assert.hasText(key, "key is null or blank");

        final JedisPool jedisPool = SpringUtils.getBean(JedisPool.class);
        final DistributedLockAutoCnf.DistributedLockProps props = SpringUtils.getBean(DistributedLockAutoCnf.DistributedLockProps.class);
        final RequestIdFactory requestIdFactory = SpringUtils.getBean(RequestIdFactory.class);
        final String effKey = props.getKeyScope() + key;
        final String requestId = requestIdFactory.create(SpringUtils.getSpringId(), Thread.currentThread().getId());

        try (Jedis jedis = jedisPool.getResource()) {
            String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
            Object result = jedis.eval(script, Collections.singletonList(effKey), Collections.singletonList(requestId));
            return RELEASE_SUCCESS.equals(result);
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

}
