/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.jwt.exception;

/**
 * @author 应卓
 */
public class JwtDecodeException extends InvalidTokenException {

    private static final long serialVersionUID = 1232483943103042944L;

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
