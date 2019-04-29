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

public class InvalidClaimException extends AuthenticationException {

    public InvalidClaimException() {
        this(null);
    }

    public InvalidClaimException(String msg) {
        super(msg);
    }

    public InvalidClaimException(String msg, Throwable t) {
        super(msg, t);
    }
}
