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

import java.util.List;
import java.util.Stack;

/**
 * @author 应卓
 * @since 1.1.10
 */
public final class DistributedLockExt implements Constant {

    public static boolean lockMultiple(List<String> keys, long expireInMillis) {
        final Stack<String> stack = new Stack<>();

        for (String key : keys) {
            if (DistributedLock.lock(key, expireInMillis)) {
                stack.push(key);
            } else {
                while (!stack.isEmpty()) {
                    final String locked = stack.pop();
                    DistributedLock.release(locked);
                }
                return false;
            }
        }
        return true;
    }

    public static boolean releaseMultiple(List<String> keys) {
        for (String key : keys) {
            if (!DistributedLock.release(key)) {
                return false;
            }
        }
        return true;
    }

    // -----------------------------------------------------------------------------------------------------------------

    private DistributedLockExt() {
    }

}
