/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.datetime;

import java.time.Year;

/**
 * @author 应卓
 * @since 1.10.28
 */
public final class Years {

    private Years() {
    }

    public static boolean isLeap(Object year) {
        if (year instanceof Integer) {
            return Year.of((int) year).isLeap();
        }

        if (year instanceof String) {
            return Year.of(Integer.parseInt((String) year)).isLeap();
        }

        throw new IllegalArgumentException("parameter type is not supported.");
    }

    public static int length(Object year) {
        if (year instanceof Integer) {
            return Year.of((int) year).length();
        }

        if (year instanceof String) {
            return Year.of(Integer.parseInt((String) year)).length();
        }
        throw new IllegalArgumentException("parameter type is not supported.");
    }

}
