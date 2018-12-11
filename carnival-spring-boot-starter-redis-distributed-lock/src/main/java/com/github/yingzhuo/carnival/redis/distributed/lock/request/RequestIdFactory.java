/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.redis.distributed.lock.request;

/**
 * @author 应卓
 */
@FunctionalInterface
public interface RequestIdFactory {

    public static final RequestIdFactory DEFAULT = new Default();

    public String create(String springId, long threadId);

    public static final class Default implements RequestIdFactory {

        private Default() {
        }

        @Override
        public String create(String springId, long threadId) {
            return java.util.Objects.requireNonNull(springId) + "." + threadId;
        }
    }

}
