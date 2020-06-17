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

import java.util.function.Supplier;

/**
 * @author 应卓
 * @since 1.6.9
 */
@FunctionalInterface
public interface Lockable extends Supplier<String> {

    public String asLockKey();

    @Override
    default String get() {
        return asLockKey();
    }

}
