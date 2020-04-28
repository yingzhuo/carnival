/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.security.jwt.refresher;

import com.github.yingzhuo.carnival.restful.security.exception.RestfulSecurityException;
import com.github.yingzhuo.carnival.restful.security.exception.TokenRefreshException;
import com.github.yingzhuo.carnival.restful.security.jwt.factory.JwtTokenFactory;
import com.github.yingzhuo.carnival.restful.security.realm.UserDetailsRealm;
import com.github.yingzhuo.carnival.restful.security.refresher.TokenRefresher;
import com.github.yingzhuo.carnival.restful.security.token.StringToken;
import com.github.yingzhuo.carnival.restful.security.token.Token;
import com.github.yingzhuo.carnival.restful.security.userdetails.UserDetails;

import java.util.Optional;

/**
 * @author 应卓
 * @since 1.6.0
 */
public abstract class AbstractJwtTokenRefresher implements TokenRefresher {

    protected final UserDetailsRealm realm;
    protected final JwtTokenFactory tokenFactory;

    public AbstractJwtTokenRefresher(UserDetailsRealm realm, JwtTokenFactory tokenFactory) {
        this.realm = realm;
        this.tokenFactory = tokenFactory;
    }

    @Override
    public final Token refresh(Token oldToken) {

        try {
            Optional<UserDetails> userDetailsOp = realm.loadUserDetails(oldToken);
            final String tokenValue = userDetailsOp.map(userDetails -> doRefresh(userDetails, oldToken)).orElse(null);

            if (tokenValue == null) {
                return null;
            } else {
                return new StringToken(tokenValue);
            }

        } catch (RestfulSecurityException e) {
            throw new TokenRefreshException(e.getMessage(), e);
        }

    }

    protected abstract String doRefresh(UserDetails userDetails, Token old);

}
