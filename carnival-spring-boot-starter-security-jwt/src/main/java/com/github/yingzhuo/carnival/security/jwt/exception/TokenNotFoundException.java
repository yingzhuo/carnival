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

/**
 * @author 应卓
 */
public class TokenNotFoundException extends AuthenticationException {

    public TokenNotFoundException() {
        this(null);
    }

    public TokenNotFoundException(String msg) {
        super(msg);
    }

    public TokenNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }
}
