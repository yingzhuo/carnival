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

/**
 * @author 应卓
 */
public class TokenNotWhitelistedException extends AuthenticationException {

    private final Token token;
    private final UserDetails userDetails;

    public TokenNotWhitelistedException(Token token, UserDetails userDetails) {
        this(token, userDetails, null);
    }

    public TokenNotWhitelistedException(Token token, UserDetails userDetails, String message) {
        super(message);
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
