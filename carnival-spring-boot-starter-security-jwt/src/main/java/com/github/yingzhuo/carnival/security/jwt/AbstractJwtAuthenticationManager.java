/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.github.yingzhuo.carnival.security.jwt.algorithm.SignatureAlgorithm;
import com.github.yingzhuo.carnival.security.jwt.algorithm.SignatureAlgorithmUtils;
import com.github.yingzhuo.carnival.security.jwt.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * @author 应卓
 */
public abstract class AbstractJwtAuthenticationManager implements AuthenticationManager {

    private static Logger LOGGER = LoggerFactory.getLogger(AbstractJwtAuthenticationManager.class);

    private SignatureAlgorithm signatureAlgorithm;
    private String secret;

    @Override
    public final Authentication authenticate(Authentication token) throws AuthenticationException {

        LOGGER.info("进入认证器");

        if (token == null) {
            throw new TokenNotFoundException();
        }

        if (!(token instanceof JwtToken)) {
            throw new UnsupportedTokenException();
        }

        try {
            Algorithm algorithm = SignatureAlgorithmUtils.toAlgorithm(signatureAlgorithm, secret);
            JWTVerifier verifier = JWT.require(algorithm).build();

            DecodedJWT jwt = verifier.verify(((JwtToken) token).getRawToken());
            doAuthenticate((JwtToken) token, jwt);
            token.setAuthenticated(true);
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

        token.setAuthenticated(true);
        return token;
    }

    protected abstract void doAuthenticate(JwtToken token, DecodedJWT jwt) throws AuthenticationException;

    public final void setSignatureAlgorithm(SignatureAlgorithm signatureAlgorithm) {
        this.signatureAlgorithm = signatureAlgorithm;
    }

    public final void setSecret(String secret) {
        this.secret = secret;
    }

}
