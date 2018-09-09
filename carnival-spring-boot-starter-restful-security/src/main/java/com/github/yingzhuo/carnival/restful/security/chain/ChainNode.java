/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.security.chain;

import com.github.yingzhuo.carnival.restful.security.parser.TokenParser;
import com.github.yingzhuo.carnival.restful.security.realm.UserDetailsRealm;

import java.util.Objects;

/**
 * @author 应卓
 */
public final class ChainNode {

    public static ChainNode of(TokenParser tokenParser, UserDetailsRealm userDetailsRealm) {
        return new ChainNode(
                Objects.requireNonNull(tokenParser),
                Objects.requireNonNull(userDetailsRealm)
        );
    }

    private final TokenParser tokenParser;
    private final UserDetailsRealm userDetailsRealm;

    private ChainNode(TokenParser tokenParser, UserDetailsRealm userDetailsRealm) {
        this.tokenParser = tokenParser;
        this.userDetailsRealm = userDetailsRealm;
    }

    public UserDetailsRealm getUserDetailsRealm() {
        return userDetailsRealm;
    }

    public TokenParser getTokenParser() {
        return tokenParser;
    }

}
