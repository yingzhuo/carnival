/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.common.util;

import java.util.Objects;

/**
 * @author 应卓
 * @since 1.1.10
 */
public final class AtoiUtils {

    public static Long toLong(String s) {
        Objects.requireNonNull(s);
        if (s.startsWith("0x") || s.startsWith("0X")) {
            return Long.parseLong(s.substring(2), 16);
        }
        if (s.startsWith("0b") || s.startsWith("0B")) {
            return Long.parseLong(s.substring(2), 2);
        }
        if (s.startsWith("0")) {
            return Long.parseLong(s.substring(1), 8);
        }
        return Long.parseLong(s, 10);
    }

    public static Integer toInt(String s) {
        Objects.requireNonNull(s);
        if (s.startsWith("0x") || s.startsWith("0X")) {
            return Integer.parseInt(s.substring(2), 16);
        }
        if (s.startsWith("0b") || s.startsWith("0B")) {
            return Integer.parseInt(s.substring(2), 2);
        }
        if (s.startsWith("0")) {
            return Integer.parseInt(s.substring(1), 8);
        }
        return Integer.parseInt(s, 10);
    }

    // -----------------------------------------------------------------------------------------------------------------

    private AtoiUtils() {
    }

}
