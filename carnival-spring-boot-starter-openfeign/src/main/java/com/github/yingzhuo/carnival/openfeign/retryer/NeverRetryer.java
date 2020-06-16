/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.openfeign.retryer;

import feign.RetryableException;
import feign.Retryer;

import java.io.IOException;
import java.io.UncheckedIOException;

/**
 * @author 应卓
 * @since 1.6.18
 */
public final class NeverRetryer implements Retryer {

    public static final NeverRetryer INSTANCE = new NeverRetryer();

    private NeverRetryer() {
    }

    @Override
    public void continueOrPropagate(RetryableException e) {
        final Throwable cause = e.getCause();

        if (cause != null) {
            if (cause instanceof IOException) {
                final IOException ioEx = (IOException) cause;
                throw new RuntimeException(new UncheckedIOException(ioEx));
            }

            if (cause instanceof RuntimeException) {
                throw (RuntimeException) cause;
            }
        }

        throw e;
    }

    @Override
    public Retryer clone() {
        return this;
    }

}
