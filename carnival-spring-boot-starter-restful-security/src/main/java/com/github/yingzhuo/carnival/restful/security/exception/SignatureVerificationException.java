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

import javax.servlet.http.HttpServletRequest;

/**
 * 签名错误
 *
 * @author 应卓
 * @see com.auth0.jwt.exceptions.SignatureVerificationException
 */
public class SignatureVerificationException extends InvalidTokenException {

    public SignatureVerificationException(HttpServletRequest request) {
        super(request);
    }

    public SignatureVerificationException(String message, HttpServletRequest request) {
        super(message, request);
    }

}
