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
 * Claim非法
 *
 * @author 应卓
 * @see com.auth0.jwt.exceptions.InvalidClaimException
 */
public class InvalidClaimException extends InvalidTokenException {

    public InvalidClaimException() {
    }

    public InvalidClaimException(HttpServletRequest request) {
        super(request);
    }

    public InvalidClaimException(String message, HttpServletRequest request) {
        super(message, request);
    }

}
