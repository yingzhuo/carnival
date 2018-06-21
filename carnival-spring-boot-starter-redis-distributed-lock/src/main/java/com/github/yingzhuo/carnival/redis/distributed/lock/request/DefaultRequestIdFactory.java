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

import java.util.Objects;

/**
 * @author 应卓
 */
public class DefaultRequestIdFactory implements RequestIdFactory {

    @Override
    public String create(String springId, long threadId) {
        final StringBuilder sb = new StringBuilder();
        sb.append(Objects.requireNonNull(springId));
        sb.append("#");
        sb.append(threadId);
        return sb.toString();
    }

}
