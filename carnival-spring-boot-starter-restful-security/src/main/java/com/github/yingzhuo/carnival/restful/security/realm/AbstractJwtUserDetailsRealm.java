/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.security.realm;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.github.yingzhuo.carnival.restful.security.exception.*;
import com.github.yingzhuo.carnival.restful.security.factory.AlgorithmFactory;
import com.github.yingzhuo.carnival.restful.security.token.StringToken;
import com.github.yingzhuo.carnival.restful.security.token.Token;
import com.github.yingzhuo.carnival.restful.security.userdetails.UserDetails;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Optional;

/**
 * @author 应卓
 */
public abstract class AbstractJwtUserDetailsRealm implements UserDetailsRealm, ApplicationContextAware {

    private Algorithm algorithm;

    @Override
    public final Optional<UserDetails> loadUserDetails(Token token) {

        if (token instanceof StringToken) {
            final String tokenValue = ((StringToken) token).getValue();
            final JWTVerifier verifier = JWT.require(algorithm).build();

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

    public Algorithm getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.setAlgorithm(applicationContext.getBean(AlgorithmFactory.class).create());
    }

}
