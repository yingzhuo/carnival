/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.security.jwt.realm;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.github.yingzhuo.carnival.restful.security.exception.UnsupportedTokenTypeException;
import com.github.yingzhuo.carnival.restful.security.jwt.exception.*;
import com.github.yingzhuo.carnival.restful.security.jwt.signature.AlgorithmFactory;
import com.github.yingzhuo.carnival.restful.security.realm.UserDetailsRealm;
import com.github.yingzhuo.carnival.restful.security.token.StringToken;
import com.github.yingzhuo.carnival.restful.security.token.Token;
import com.github.yingzhuo.carnival.restful.security.userdetails.UserDetails;
import com.github.yingzhuo.carnival.spring.SpringUtils;

import java.util.Optional;

/**
 * @author 应卓
 */
public abstract class AbstractJwtUserDetailsRealm implements UserDetailsRealm {

    private int order = 0;

    @Override
    public final Optional<UserDetails> loadUserDetails(Token token) {

        if (token instanceof StringToken) {
            final String tokenValue = ((StringToken) token).getValue();
            final JWTVerifier verifier = JWT.require(SpringUtils.getBean(AlgorithmFactory.class).create()).build();

            try {
                return Optional.ofNullable(getUserDetails(token, verifier.verify(tokenValue)));
            } catch (com.auth0.jwt.exceptions.AlgorithmMismatchException ex) {
                throw new AlgorithmMismatchException(ex.getMessage(), ex);
            } catch (com.auth0.jwt.exceptions.TokenExpiredException ex) {
                throw new TokenExpiredException(ex.getMessage(), ex);
            } catch (com.auth0.jwt.exceptions.SignatureVerificationException ex) {
                throw new SignatureVerificationException(ex.getMessage(), ex);
            } catch (com.auth0.jwt.exceptions.InvalidClaimException ex) {
                throw new InvalidClaimException(ex.getMessage(), ex);
            } catch (com.auth0.jwt.exceptions.JWTDecodeException ex) {
                throw new JwtDecodeException(ex.getMessage(), ex);
            }
        }

        throw new UnsupportedTokenTypeException();
    }

    protected abstract UserDetails getUserDetails(Token token, DecodedJWT jwt);

    @Override
    public int getOrder() {
        return this.order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

}
