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
 * @see RestfulSecurityException
 */
public class UnsupportedTokenTypeException extends AuthenticationException {

    private static final long serialVersionUID = -3898427217629257270L;

    public UnsupportedTokenTypeException() {
        super();
    }

    public UnsupportedTokenTypeException(String message) {
        super(message);
    }

    public UnsupportedTokenTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnsupportedTokenTypeException(Throwable cause) {
        super(cause);
    }
}
