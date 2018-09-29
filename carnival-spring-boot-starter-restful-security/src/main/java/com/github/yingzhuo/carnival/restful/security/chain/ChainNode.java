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

import com.github.yingzhuo.carnival.restful.security.RestfulSecurityNode;
import com.github.yingzhuo.carnival.restful.security.parser.TokenParser;
import com.github.yingzhuo.carnival.restful.security.realm.UserDetailsRealm;
import com.github.yingzhuo.carnival.restful.security.token.Token;
import com.github.yingzhuo.carnival.restful.security.userdetails.UserDetails;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

/**
 * @author 应卓
 */
public final class ChainNode implements RestfulSecurityNode {

    public static ChainNode of(TokenParser tokenParser, UserDetailsRealm userDetailsRealm) {
        return new ChainNode(tokenParser, userDetailsRealm);
    }

    private final TokenParser tokenParser;
    private final UserDetailsRealm userDetailsRealm;

    private ChainNode(TokenParser tokenParser, UserDetailsRealm userDetailsRealm) {
        this.tokenParser = Objects.requireNonNull(tokenParser);
        this.userDetailsRealm = Objects.requireNonNull(userDetailsRealm);
    }

    public UserDetailsRealm getUserDetailsRealm() {
        return userDetailsRealm;
    }

    public TokenParser getTokenParser() {
        return tokenParser;
    }

    @Override
    public Optional<Token> parse(NativeWebRequest webRequest, Locale locale) {
        return getTokenParser().parse(webRequest, locale);
    }

    @Override
    public Optional<UserDetails> loadUserDetails(Token token) {
        return getUserDetailsRealm().loadUserDetails(token);
    }

}
