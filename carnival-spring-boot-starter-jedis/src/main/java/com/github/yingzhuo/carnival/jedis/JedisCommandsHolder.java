/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.jedis;

import redis.clients.jedis.JedisCommands;

import java.util.function.Supplier;

/**
 * @author 应卓
 * @since 1.6.7
 */
public interface JedisCommandsHolder extends Supplier<JedisCommands> {

    @Override
    public JedisCommands get();

    public void close(JedisCommands commands);

}
