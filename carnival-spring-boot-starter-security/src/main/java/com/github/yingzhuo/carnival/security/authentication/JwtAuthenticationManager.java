/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.security.authentication;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.github.yingzhuo.carnival.security.exception.*;
import com.github.yingzhuo.carnival.security.token.MutableToken;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author 应卓
 * @since 1.10.2
 */
public abstract class JwtAuthenticationManager implements TokenAuthenticationManager {

    private final Algorithm algorithm;

    public JwtAuthenticationManager(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    @Override
    public final Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (!(authentication instanceof MutableToken)) {
            throw new UnsupportedTokenException(null);
        }

        final MutableToken token = (MutableToken) authentication;

        try {
            final JWTVerifier verifier = JWT.require(algorithm).build();

            String tokenValue = token.getKey();
            DecodedJWT jwt = verifier.verify(tokenValue);

            final UserDetails userDetails = doAuthenticate(tokenValue, jwt);

            if (userDetails == null) {
                throw new UserDetailsNotFoundException(null);
            }

            if (!userDetails.isEnabled()) {
                throw new DisabledException(null);
            }

            if (!userDetails.isAccountNonExpired()) {
                throw new AccountExpiredException(null);
            }

            if (!userDetails.isAccountNonLocked()) {
                throw new LockedException(null);
            }

            if (!userDetails.isCredentialsNonExpired()) {
                throw new CredentialsExpiredException(null);
            }

            token.setUserDetails(userDetails);
            token.setDetails(null);
            token.setAuthenticated(true);
            return token;

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

    protected abstract UserDetails doAuthenticate(String rawToken, DecodedJWT jwt) throws AuthenticationException;

    public Algorithm getAlgorithm() {
        return algorithm;
    }
}
