/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.security.token;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Objects;
import java.util.Optional;

/**
 * @author 应卓
 * @since 1.10.17
 */
public class TokenAuthenticationToken extends AbstractAuthenticationToken implements Token {

    private final String key;
    private final UserDetails userDetails;

    public TokenAuthenticationToken(String key) {
        this(key, null);
    }

    public TokenAuthenticationToken(String key, UserDetails userDetails) {
        super(Optional.ofNullable(userDetails).map(UserDetails::getAuthorities).orElse(null));
        this.key = Objects.requireNonNull(key);
        this.userDetails = userDetails;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public UserDetails getUserDetails() {
        return this.userDetails;
    }

    @Override
    public final Object getCredentials() {
        return getKey();
    }

    @Override
    public final Object getPrincipal() {
        return getUserDetails();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName()).append(" [");
        sb.append("Key=").append(getKey()).append(", ");
        sb.append("Principal=").append(getPrincipal()).append(", ");
        sb.append("Credentials=[PROTECTED], ");
        sb.append("Authenticated=").append(isAuthenticated()).append(", ");
        sb.append("Details=").append(getDetails()).append(", ");
        sb.append("Granted Authorities=").append(getAuthorities());
        sb.append("]");
        return sb.toString();
    }

}
