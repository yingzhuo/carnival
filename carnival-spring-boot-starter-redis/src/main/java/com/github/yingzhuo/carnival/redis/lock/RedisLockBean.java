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

import java.time.Duration;

/**
 * @author 应卓
 * @since 1.6.9
 */
public interface RedisLockBean {

    public default boolean lock(String key) {
        return lock(key, null);
    }

    public boolean lock(String key, Duration max);

    public boolean release(String key);

}
