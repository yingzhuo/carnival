/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.openfeign.util;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static feign.Request.Options;

/**
 * @author 应卓
 * @since 1.6.17
 */
public final class OptionsUtils {

    private OptionsUtils() {
    }

    public static Options newInstance(Duration connectTimeout, Duration readTimeout) {
        return newInstance(connectTimeout, readTimeout, true);
    }

    public static Options newInstance(Duration connectTimeout, Duration readTimeout, boolean followRedirects) {
        return new Options(
                connectTimeout.getSeconds(), TimeUnit.SECONDS,
                readTimeout.getSeconds(), TimeUnit.SECONDS,
                followRedirects
        );
    }

    public static Options newInstance() {
        return new Options();
    }

}
