/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.security.exception;

import org.springframework.security.authentication.BadCredentialsException;

/**
 * @author 应卓
 * @since 1.10.2
 */
public class SignatureVerificationException extends BadCredentialsException {

    public SignatureVerificationException(String msg) {
        super(msg);
    }

    public SignatureVerificationException(String msg, Throwable t) {
        super(msg, t);
    }

}
