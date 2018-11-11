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
 * 签名错误
 *
 * @author 应卓
 * @see com.auth0.jwt.exceptions.SignatureVerificationException
 */
public class SignatureVerificationException extends InvalidTokenException {

    private static final long serialVersionUID = 8879716562937300490L;

    public SignatureVerificationException() {
        super();
    }

    public SignatureVerificationException(String message) {
        super(message);
    }

    public SignatureVerificationException(String message, Throwable cause) {
        super(message, cause);
    }

    public SignatureVerificationException(Throwable cause) {
        super(cause);
    }

}
