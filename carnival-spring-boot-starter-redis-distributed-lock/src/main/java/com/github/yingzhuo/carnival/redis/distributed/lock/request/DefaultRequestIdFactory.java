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

import lombok.val;

import java.util.Objects;
import java.util.UUID;

/**
 * @author 应卓
 */
public class DefaultRequestIdFactory implements RequestIdFactory {

    @Override
    public String create(String springId, long threadId) {
        val build = new StringBuilder();
        build.append(Objects.requireNonNull(springId));
        build.append(".");
        build.append(threadId);
        return build.toString();
    }

}
