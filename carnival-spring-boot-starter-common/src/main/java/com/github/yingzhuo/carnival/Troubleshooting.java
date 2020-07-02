/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival;

import lombok.extern.slf4j.Slf4j;

/**
 * @author 应卓
 * @since 1.6.24
 */
@Slf4j(topic = "troubleshooting")
public final class Troubleshooting {

    static boolean enabled = false;

    private Troubleshooting() {
    }

    public static void troubleshooting(String format, Object... arguments) {
        if (enabled) log.debug(format, arguments);
    }

}
