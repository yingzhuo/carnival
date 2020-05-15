/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.jedis.util;

import com.github.yingzhuo.carnival.jedis.JedisCommandsHolder;
import com.github.yingzhuo.carnival.spring.SpringUtils;
import redis.clients.jedis.JedisCommands;

/**
 * @author 应卓
 * @since 1.6.7
 */
public final class JedisUtils {

    private JedisUtils() {
    }

    public static JedisCommands getCommands() {
        return SpringUtils.getBean(JedisCommandsHolder.class).get();
    }

    public static void closeCommands(JedisCommands commands) {
        SpringUtils.getBean(JedisCommandsHolder.class).close(commands);
    }

}
