package com.github.yingzhuo.carnival.security.jwt.exception;

import org.springframework.security.core.AuthenticationException;

public class UnsupportedTokenException extends AuthenticationException {

    public UnsupportedTokenException(String msg, Throwable t) {
        super(msg, t);
    }

    public UnsupportedTokenException(String msg) {
        super(msg);
    }

}
