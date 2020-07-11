/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.openfeign.exception;

import java.io.IOException;
import java.io.UncheckedIOException;

/**
 * 包装{@link java.net.ConnectException}
 *
 * @author 应卓
 * @since 1.6.25
 */
public class ConnectionException extends UncheckedIOException {

    // java.net.ConnectException 是要检查异常，非常讨厌

    public ConnectionException(String message, IOException cause) {
        super(message, cause);
    }

    public ConnectionException(IOException cause) {
        super(cause);
    }

}
