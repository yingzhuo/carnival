/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.jedis.lock;

import com.github.yingzhuo.carnival.jedis.util.JedisUtils;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisCommands;

import java.util.Collections;
import java.util.Objects;

/**
 * 分布式锁
 *
 * @author 应卓
 * @since 1.6.7
 */
@Slf4j
public final class DistributedLock {

    private final static String LOCK_SUCCESS = "OK";
    private final static Long RELEASE_SUCCESS = 1L;
    private final static String SET_IF_NOT_EXIST = "NX";
    private final static String SET_WITH_EXPIRE_TIME = "PX";

    // 待初始化
    static boolean enabled = false;
    static String prefix;
    static String suffix;
    static String springId;
    static long timeToLive; // 毫秒
    static DistributedLockExceptionThrower exceptionThrower;

    private DistributedLock() {
    }

    public static boolean lock(String key) {
        if (!enabled) {
            throw new IllegalArgumentException("DistributedLock not enabled.");
        }

        Objects.requireNonNull(key);

        final long threadId = Thread.currentThread().getId();
        final String effKey = prefix + key + suffix;
        final String requestId = createRequestId(threadId);

        log.trace("try to lock. key = {}, value = {}", effKey, requestId);

        final JedisCommands commands = JedisUtils.getJedisCommands();

        String result = commands.set(
                effKey,
                requestId,
                SET_IF_NOT_EXIST,
                SET_WITH_EXPIRE_TIME,
                timeToLive);

        try {
            val ok = LOCK_SUCCESS.equals(result);
            if (!ok && exceptionThrower != null) {
                exceptionThrower.raiseIfNotAbleToLock(springId, threadId);
            }
            return ok;
        } finally {
            JedisUtils.close(commands);
        }
    }

    public static boolean release(String key) {
        if (!enabled) {
            throw new IllegalArgumentException("DistributedLock not enabled.");
        }

        Objects.requireNonNull(key);

        final long threadId = Thread.currentThread().getId();
        final String effKey = prefix + key + suffix;
        final String requestId = createRequestId(threadId);

        log.trace("try to release. key = {}, value = {}", effKey, requestId);

        final JedisCommands commands = JedisUtils.getJedisCommands();

        final String lua = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";

        final Object result;

        if (commands instanceof Jedis) {
            result = ((Jedis) commands).eval(lua, Collections.singletonList(effKey), Collections.singletonList(requestId));
        } else {
            result = ((JedisCluster) commands).eval(lua, Collections.singletonList(effKey), Collections.singletonList(requestId));
        }

        try {
            val ok = RELEASE_SUCCESS.equals(result);
            if (!ok && exceptionThrower != null) {
                exceptionThrower.raiseIfNotAbleToRelease(springId, threadId);
            }
            return ok;
        } finally {
            JedisUtils.close(commands);
        }
    }

    public static boolean lock(long key) {
        return lock(String.valueOf(key));
    }

    public static boolean release(long key) {
        return release(String.valueOf(key));
    }

    public static boolean lock(int key) {
        return lock(String.valueOf(key));
    }

    public static boolean release(int key) {
        return release(String.valueOf(key));
    }

    public static boolean lock(Lockable lockable) {
        return lock(lockable.toLockableKey());
    }

    public static boolean release(Lockable lockable) {
        return release(lockable.toLockableKey());
    }

    public static void waitAndRun(String key, Runnable runnable) {
        Objects.requireNonNull(runnable);

        while (true) {
            if (lock(key)) {
                runnable.run();
                release(key);
                return;
            }
        }
    }

    public static void waitAndRun(int key, Runnable runnable) {
        waitAndRun(String.valueOf(key), runnable);
    }

    public static void waitAndRun(long key, Runnable runnable) {
        waitAndRun(String.valueOf(key), runnable);
    }

    private static String createRequestId(long threadId) {
        return springId + "." + threadId;
    }

}
