/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.redis.distributed.lock.support;

import java.util.Objects;

/**
 * @author 应卓
 */
public class RequestIdCreatorImpl implements RequestIdCreator {

    @Override
    public String create(String springId, long threadId) {
        return Objects.requireNonNull(springId) + "." + threadId;
    }

}
