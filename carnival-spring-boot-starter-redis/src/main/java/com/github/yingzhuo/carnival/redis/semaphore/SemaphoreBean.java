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

import java.time.Duration;

/**
 * @author 应卓
 * @since 1.6.19
 */
public interface SemaphoreBean {

    public default boolean lock(String key, int size) {
        return lock(key, size, null);
    }

    public boolean lock(String key, int size, Duration max);

    public boolean release(String key);

}
