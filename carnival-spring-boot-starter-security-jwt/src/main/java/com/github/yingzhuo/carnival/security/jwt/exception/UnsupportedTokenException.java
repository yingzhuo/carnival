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
public class UnsupportedTokenException extends AuthenticationException {

    public UnsupportedTokenException() {
        this(null);
    }

    public UnsupportedTokenException(String msg) {
        super(msg);
    }

    public UnsupportedTokenException(String msg, Throwable t) {
        super(msg, t);
    }

}
