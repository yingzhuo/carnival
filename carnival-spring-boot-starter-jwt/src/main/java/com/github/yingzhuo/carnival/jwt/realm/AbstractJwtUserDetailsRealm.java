/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.jwt.realm;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.github.yingzhuo.carnival.jwt.SignatureAlgorithm;
import com.github.yingzhuo.carnival.jwt.exception.*;
import com.github.yingzhuo.carnival.jwt.props.JwtProps;
import com.github.yingzhuo.carnival.jwt.util.InternalUtils;
import com.github.yingzhuo.carnival.restful.security.realm.UserDetailsRealm;
import com.github.yingzhuo.carnival.restful.security.token.StringToken;
import com.github.yingzhuo.carnival.restful.security.token.Token;
import com.github.yingzhuo.carnival.restful.security.userdetails.UserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

/**
 * @author 应卓
 */
@Slf4j
public abstract class AbstractJwtUserDetailsRealm implements UserDetailsRealm {

    private String secret;
    private SignatureAlgorithm signatureAlgorithm;

    @Autowired
    private JwtProps jwtProps;

    public AbstractJwtUserDetailsRealm() {
    }

    @Override
    public final Optional<UserDetails> loadUserDetails(Token token) {

        if (token instanceof StringToken) {
            final String tokenValue = ((StringToken) token).getValue();

            Algorithm algorithm = InternalUtils.toAlgorithm(signatureAlgorithm, secret);
            JWTVerifier verifier = JWT.require(algorithm).build();
            try {
                DecodedJWT jwt = verifier.verify(tokenValue);
                return Optional.ofNullable(getUserDetails(jwt));
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

        return Optional.empty();
    }

    protected abstract UserDetails getUserDetails(DecodedJWT jwt);

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public void setSignatureAlgorithm(SignatureAlgorithm signatureAlgorithm) {
        this.signatureAlgorithm = signatureAlgorithm;
    }

}
