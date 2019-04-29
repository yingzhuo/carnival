/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.security.jwt.exception;

import org.springframework.security.core.AuthenticationException;

public class TokenExpiredException extends AuthenticationException {

    public TokenExpiredException() {
        this(null);
    }

    public TokenExpiredException(String msg) {
        super(msg);
    }

    public TokenExpiredException(String msg, Throwable t) {
        super(msg, t);
    }

}
