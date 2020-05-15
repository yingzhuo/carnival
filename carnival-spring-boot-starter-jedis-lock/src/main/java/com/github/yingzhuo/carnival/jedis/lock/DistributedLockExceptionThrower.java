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

import com.github.yingzhuo.carnival.jedis.lock.exception.NotAbleToLockException;
import com.github.yingzhuo.carnival.jedis.lock.exception.NotAbleToReleaseException;

/**
 * @author 应卓
 * @since 1.6.7
 */
public interface DistributedLockExceptionThrower {

    public static DistributedLockExceptionThrower getDefault() {
        return new DistributedLockExceptionThrower() {
            @Override
            public void raiseIfNotAbleToLock(String springId, Long threadId) {
                throw new NotAbleToLockException(springId, threadId);
            }

            @Override
            public void raiseIfNotAbleToRelease(String springId, Long threadId) {
                throw new NotAbleToReleaseException(springId, threadId);
            }
        };
    }

    public void raiseIfNotAbleToLock(String springId, Long threadId);

    public void raiseIfNotAbleToRelease(String springId, Long threadId);

}
