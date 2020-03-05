/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.exception;

/**
 * 自洽性错误
 *
 * @author 应卓
 * @since 1.4.5
 */
public class SelfConsistentException extends RuntimeException {

    public SelfConsistentException() {
    }

    public SelfConsistentException(String message) {
        super(message);
    }

    public SelfConsistentException(String message, Throwable cause) {
        super(message, cause);
    }

    public SelfConsistentException(Throwable cause) {
        super(cause);
    }

}
