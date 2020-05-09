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

import com.google.common.base.Preconditions;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.recipes.atomic.DistributedAtomicLong;
import org.apache.curator.retry.RetryOneTime;

/**
 * @author 应卓
 * @since 1.3.3
 */
public final class CounterUtils extends AbstractUtils {

    private CounterUtils() {
    }

    public static DistributedAtomicLong newDistributedAtomicLong(String path) {
        return newDistributedAtomicLong(path, null);
    }

    public static DistributedAtomicLong newDistributedAtomicLong(String path, RetryPolicy retryPolicy) {
        Preconditions.checkArgument(path != null);
        return new DistributedAtomicLong(getClient(), betterPath(path), retryPolicy != null ? retryPolicy : getDefaultRetryPolicy());
    }

    public static RetryPolicy getDefaultRetryPolicy() {
        return new RetryOneTime(500);
    }

}
