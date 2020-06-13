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

import java.io.UncheckedIOException;
import java.net.SocketException;

/**
 * @author 应卓
 * @since 1.6.17
 */
public final class NeverRetryer implements Retryer {

    public static final Retryer INSTANCE = new NeverRetryer();

    private NeverRetryer() {
    }

    @Override
    public void continueOrPropagate(RetryableException e) {
        Throwable cause = e.getCause();
        if (cause != null) {

            // 连接故障
            if ((cause instanceof SocketException)) {
                throw new UncheckedIOException((SocketException) cause);
            }

        }
        throw e;
    }

    @Override
    public Retryer clone() {
        return this;
    }

}
