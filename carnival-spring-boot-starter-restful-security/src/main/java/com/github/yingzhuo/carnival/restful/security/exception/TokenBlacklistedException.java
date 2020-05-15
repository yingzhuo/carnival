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

import com.github.yingzhuo.carnival.restful.security.token.Token;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 应卓
 */
public class TokenBlacklistedException extends AuthenticationException {

    private final Token Token;

    public TokenBlacklistedException(HttpServletRequest request, Token token) {
        super(request);
        Token = token;
    }

    public TokenBlacklistedException(String message, HttpServletRequest request, Token token) {
        super(message, request);
        Token = token;
    }

    public com.github.yingzhuo.carnival.restful.security.token.Token getToken() {
        return Token;
    }

}
