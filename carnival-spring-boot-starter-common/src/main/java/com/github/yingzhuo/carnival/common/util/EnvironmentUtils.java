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

/**
 * @author 应卓
 * @since 1.1.10
 */
public final class EnvironmentUtils {

    public static Long getLongOrDefault(String envName, Long defaultValue) {
        try {
            return AtoiUtils.toLong(System.getenv(envName));
        } catch (NumberFormatException | NullPointerException e) {
            return defaultValue;
        }
    }

    public static Integer getIntOrDefault(String envName, Integer defaultValue) {
        try {
            return AtoiUtils.toInt(System.getenv(envName));
        } catch (NumberFormatException | NullPointerException e) {
            return defaultValue;
        }
    }

    public static String getStringOrDefault(String envName, String defaultValue) {
        String val = System.getenv(envName);
        if (val == null) {
            return defaultValue;
        }
        return val;
    }

    // -----------------------------------------------------------------------------------------------------------------

    private EnvironmentUtils() {
    }

}
