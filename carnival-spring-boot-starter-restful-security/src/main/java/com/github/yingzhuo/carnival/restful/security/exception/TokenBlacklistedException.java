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

import java.util.Objects;

/**
 * @author 应卓
 */
public class TokenBlacklistedException extends AuthenticationException {

    private final Token token;

    public TokenBlacklistedException(Token token) {
        this.token = Objects.requireNonNull(token);
    }

    public TokenBlacklistedException(Token token, String message) {
        super(message);
        this.token = Objects.requireNonNull(token);
    }

}
