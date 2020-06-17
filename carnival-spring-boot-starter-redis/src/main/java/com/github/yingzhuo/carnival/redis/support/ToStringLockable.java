/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.redis.support;

/**
 * @author 应卓
 * @since 1.6.12
 */
public final class ToStringLockable implements Lockable {

    private final String toString;

    private ToStringLockable(Object delegate) {
        this.toString = delegate.toString();
    }

    public static Lockable of(Object o) {
        return new ToStringLockable(0);
    }

    @Override
    public String asLockKey() {
        return toString;
    }

    @Override
    public String toString() {
        return toString;
    }

}
