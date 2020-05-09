/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.curator.util;

import org.apache.curator.framework.recipes.locks.InterProcessMutex;

import java.util.concurrent.TimeUnit;

/**
 * @author 应卓
 * @since 1.3.0
 */
public final class DistributedLockUtils extends AbstractUtils {

    private DistributedLockUtils() {
    }

    public static InterProcessMutex createInterProcessMutex(String path) {
        path = betterPath(path);
        return new InterProcessMutex(getClient(), path);
    }

    public static boolean lock(InterProcessMutex mutex) {
        return lock(mutex, 0L);
    }

    public static boolean lock(InterProcessMutex mutex, long timeoutMs) {
        try {
            return mutex.acquire(timeoutMs, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            return false;
        }
    }

    // ------------------------------------------------------------------------------------------------------------

    public static void release(InterProcessMutex mutex) {
        try {
            mutex.release();
        } catch (Exception ignore) {
        }
    }

}
