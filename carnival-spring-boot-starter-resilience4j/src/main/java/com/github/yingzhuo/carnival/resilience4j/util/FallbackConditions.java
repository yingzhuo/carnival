/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.resilience4j.util;

import com.github.yingzhuo.carnival.exception.business.BusinessException;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.function.Predicate;

/**
 * @author 应卓
 * @since 1.6.18
 */
public final class FallbackConditions {

    private FallbackConditions() {
    }

    public static Predicate<Exception> all() {
        return e -> true;
    }

    public static Predicate<Exception> io() {
        return e -> {
            if (e instanceof IOException) {
                return true;
            }

            if (e instanceof UncheckedIOException) {
                return true;
            }

            return false;
        };
    }

    public static Predicate<Exception> notIO() {
        return e -> !io().test(e);
    }

    public static Predicate<Exception> businessException() {
        return e -> e instanceof BusinessException;
    }

    public static Predicate<Exception> notBusinessException() {
        return e -> !(e instanceof BusinessException);
    }

}
