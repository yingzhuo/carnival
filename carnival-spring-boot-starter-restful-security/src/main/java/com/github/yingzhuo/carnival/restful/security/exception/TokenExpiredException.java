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
 * 令牌过期
 *
 * @author 应卓
 * @see com.auth0.jwt.exceptions.TokenExpiredException
 */
public class TokenExpiredException extends InvalidTokenException {

    public TokenExpiredException(HttpServletRequest request) {
        super(request);
    }

    public TokenExpiredException(String message, HttpServletRequest request) {
        super(message, request);
    }

}
