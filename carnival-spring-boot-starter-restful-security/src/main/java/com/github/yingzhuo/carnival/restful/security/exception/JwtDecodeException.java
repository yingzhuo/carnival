/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.security.exception;

/**
 * @author 应卓
 */
public class JwtDecodeException extends InvalidTokenException {

    public JwtDecodeException() {
        super();
    }

    public JwtDecodeException(String message) {
        super(message);
    }

    public JwtDecodeException(String message, Throwable cause) {
        super(message, cause);
    }

    public JwtDecodeException(Throwable cause) {
        super(cause);
    }

}
