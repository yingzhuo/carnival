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
import com.github.yingzhuo.carnival.restful.security.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 应卓
 */
public class TokenNotWhitelistedException extends AuthenticationException {

    private final Token token;
    private final UserDetails userDetails;

    public TokenNotWhitelistedException(Token token, UserDetails userDetails) {
        super();
        this.token = token;
        this.userDetails = userDetails;
    }

    public TokenNotWhitelistedException(HttpServletRequest request, Token token, UserDetails userDetails) {
        super(request);
        this.token = token;
        this.userDetails = userDetails;
    }

    public TokenNotWhitelistedException(String message, HttpServletRequest request, Token token, UserDetails userDetails) {
        super(message, request);
        this.token = token;
        this.userDetails = userDetails;
    }

    public Token getToken() {
        return token;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

}
