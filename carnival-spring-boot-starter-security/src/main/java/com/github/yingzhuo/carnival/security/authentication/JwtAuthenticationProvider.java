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
import com.auth0.jwt.interfaces.Verification;
import com.github.yingzhuo.carnival.security.exception.*;
import com.github.yingzhuo.carnival.security.jwt.JwtCustomizer;
import com.github.yingzhuo.carnival.security.token.MutableToken;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Objects;

/**
 * @author 应卓
 * @since 1.10.2
 */
public abstract class JwtAuthenticationProvider implements TokenAuthenticationProvider {

    private final Algorithm algorithm;
    private final JwtCustomizer jwtCustomizer;

    public JwtAuthenticationProvider(Algorithm algorithm) {
        this(algorithm, null);
    }

    public JwtAuthenticationProvider(Algorithm algorithm, JwtCustomizer jwtCustomizer) {
        this.algorithm = Objects.requireNonNull(algorithm);
        this.jwtCustomizer = jwtCustomizer != null ? jwtCustomizer : v -> v;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return MutableToken.class.isAssignableFrom(authentication);
    }

    @Override
    public final Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (!(authentication instanceof MutableToken)) {
            throw new UnsupportedTokenException(null);
        }

        final MutableToken token = (MutableToken) authentication;

        try {
            final Verification verification = JWT.require(algorithm);
            final JWTVerifier verifier = jwtCustomizer.customize(verification).build();

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
