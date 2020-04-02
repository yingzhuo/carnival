/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.security;

import com.github.yingzhuo.carnival.restful.security.parser.TokenParser;
import com.github.yingzhuo.carnival.restful.security.realm.UserDetailsRealm;
import com.github.yingzhuo.carnival.restful.security.token.Token;
import com.github.yingzhuo.carnival.restful.security.userdetails.UserDetails;
import org.springframework.core.Ordered;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Objects;
import java.util.Optional;

/**
 * @author 应卓
 * @since 1.2.1
 */
public interface RestfulSecurityService extends TokenParser, UserDetailsRealm, Ordered {

    public static RestfulSecurityService create(TokenParser parser, UserDetailsRealm realm) {
        return new Default(parser, realm);
    }

    public static RestfulSecurityService create(TokenParser parser, UserDetailsRealm realm, int order) {
        return new Default(parser, realm, order);
    }

    @Override
    public default int getOrder() {
        return 0;
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * @since 1.4.10
     */
    public static class Default implements RestfulSecurityService {

        private final TokenParser tokenParser;
        private final UserDetailsRealm userDetailsRealm;
        private final int order;

        public Default(TokenParser parser, UserDetailsRealm realm, int order) {
            this.tokenParser = Objects.requireNonNull(parser);
            this.userDetailsRealm = Objects.requireNonNull(realm);
            this.order = order;
        }

        public Default(TokenParser parser, UserDetailsRealm realm) {
            this(parser, realm, 0);
        }

        @Override
        public Optional<Token> parse(NativeWebRequest webRequest) {
            return this.tokenParser.parse(webRequest);
        }

        @Override
        public Optional<UserDetails> loadUserDetails(Token token) {
            return this.userDetailsRealm.loadUserDetails(token);
        }

        @Override
        public int getOrder() {
            return this.order;
        }
    }

}
