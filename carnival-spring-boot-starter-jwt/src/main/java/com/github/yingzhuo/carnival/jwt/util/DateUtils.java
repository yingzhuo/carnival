/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.jwt.util;

import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author 应卓
 */
public final class DateUtils {

    private DateUtils() {
    }

    public static Date afterNow(long millis) {
        return afterNow(millis, TimeUnit.MILLISECONDS);
    }

    public static Date afterNow(long duration, TimeUnit timeUnit) {
        Objects.requireNonNull(timeUnit);
        return new Date(System.currentTimeMillis() + timeUnit.toMillis(duration));
    }

    public static Date beforeNow(long millis) {
        return beforeNow(millis, TimeUnit.MILLISECONDS);
    }

    public static Date beforeNow(long duration, TimeUnit timeUnit) {
        Objects.requireNonNull(timeUnit);
        return new Date(System.currentTimeMillis() - timeUnit.toMillis(duration));
    }

}
