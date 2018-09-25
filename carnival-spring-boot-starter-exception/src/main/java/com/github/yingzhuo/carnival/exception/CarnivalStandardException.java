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
 * @author 应卓
 */
public class CarnivalStandardException extends RuntimeException {

    private static final long serialVersionUID = -6282437225036910332L;

    public CarnivalStandardException() {
        super();
    }

    public CarnivalStandardException(String message) {
        super(message);
    }

    public CarnivalStandardException(String message, Throwable cause) {
        super(message, cause);
    }

    public CarnivalStandardException(Throwable cause) {
        super(cause);
    }

    public CarnivalStandardException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
