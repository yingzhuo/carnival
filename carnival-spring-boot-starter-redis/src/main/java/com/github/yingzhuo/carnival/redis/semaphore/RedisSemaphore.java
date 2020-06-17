/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.redis.semaphore;

import com.github.yingzhuo.carnival.redis.support.Lockable;
import com.github.yingzhuo.carnival.spring.SpringUtils;

import java.time.Duration;

/**
 * @author 应卓
 * @since 1.6.19
 */
public final class RedisSemaphore {

    private RedisSemaphore() {
    }

    public static boolean lock(String key, int size) {
        return getBean().lock(key, size);
    }

    public static boolean lock(String key, int size, Duration timeout) {
        return getBean().lock(key, size, timeout);
    }

    public static boolean release(String key) {
        return getBean().release(key);
    }

    public static boolean lock(Lockable key, int size) {
        return getBean().lock(key.asLockKey(), size);
    }

    public static boolean lock(Lockable key, int size, Duration timeout) {
        return getBean().lock(key.asLockKey(), size, timeout);
    }

    public static boolean release(Lockable key) {
        return getBean().release(key.asLockKey());
    }

    public static boolean lock(long key, int size) {
        return getBean().lock(String.valueOf(key), size);
    }

    public static boolean lock(long key, int size, Duration timeout) {
        return getBean().lock(String.valueOf(key), size, timeout);
    }

    public static boolean release(long key) {
        return getBean().release(String.valueOf(key));
    }

    private static SemaphoreBean getBean() {
        return SpringUtils.getBean(SemaphoreBean.class);
    }

}
