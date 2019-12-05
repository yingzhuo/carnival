/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.curator.lock;

import com.github.yingzhuo.carnival.curator.autoconfig.CuratorClientAutoConfig;
import com.github.yingzhuo.carnival.spring.SpringUtils;
import lombok.var;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;

import java.util.concurrent.TimeUnit;

/**
 * @author 应卓
 * @since 1.3.0
 */
public final class DistributedLockUtils extends AbstractUtils {

    public static InterProcessMutex createMutex(String key) {

        var prefix = getKeyPrefix();
        if (!prefix.endsWith("/")) {
            prefix = prefix + "/";
        }

        if (key.startsWith("/")) {
            key = key.substring(1);
        }

        return new InterProcessMutex(getClient(), prefix + key);
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

    public static void release(InterProcessMutex mutex) {
        try {
            mutex.release();
        } catch (Exception ignore) {
        }
    }

    // ------------------------------------------------------------------------------------------------------------

    private static String getKeyPrefix() {
        try {
            return SpringUtils.getBean(CuratorClientAutoConfig.CuratorClientProps.class).getDistributedLock().getKeyPrefix();
        } catch (Exception e) {
            return "";
        }
    }

    // ------------------------------------------------------------------------------------------------------------

    private DistributedLockUtils() {
    }

}
