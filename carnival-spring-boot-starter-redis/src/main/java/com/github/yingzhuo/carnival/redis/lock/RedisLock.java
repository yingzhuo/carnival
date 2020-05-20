/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.redis.lock;

import com.github.yingzhuo.carnival.spring.SpringUtils;

import java.time.Duration;

/**
 * 锁
 *
 * @author 应卓
 * @since 1.6.9
 */
public final class RedisLock {

    private RedisLock() {
    }

    public static boolean lock(String key) {
        return getBean().lock(key);
    }

    public static boolean lock(String key, Duration timeout) {
        return getBean().lock(key, timeout);
    }

    public static boolean release(String key) {
        return getBean().release(key);
    }

    // ----------------------------------------------------------------------------------------------------------------

    public static boolean lock(Lockable key) {
        return getBean().lock(key.asLockKey());
    }

    public static boolean lock(Lockable key, Duration timeout) {
        return getBean().lock(key.asLockKey(), timeout);
    }

    public static boolean release(Lockable key) {
        return getBean().release(key.asLockKey());
    }

    // ----------------------------------------------------------------------------------------------------------------


    public static boolean lock(long key) {
        return getBean().lock(String.valueOf(key));
    }

    public static boolean lock(long key, Duration timeout) {
        return getBean().lock(String.valueOf(key), timeout);
    }

    public static boolean release(long key) {
        return getBean().release(String.valueOf(key));
    }

    // ----------------------------------------------------------------------------------------------------------------


    private static RedisLockBean getBean() {
        return SpringUtils.getBean(RedisLockBean.class);
    }

}
