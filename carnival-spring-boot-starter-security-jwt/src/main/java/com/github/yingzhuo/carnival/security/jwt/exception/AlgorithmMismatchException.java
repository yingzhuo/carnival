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

public class AlgorithmMismatchException extends AuthenticationException {

    public AlgorithmMismatchException() {
        this(null);
    }

    public AlgorithmMismatchException(String msg) {
        super(msg);
    }

    public AlgorithmMismatchException(String msg, Throwable t) {
        super(msg, t);
    }


}
