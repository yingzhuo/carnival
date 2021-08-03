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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

import static org.springframework.security.core.authority.AuthorityUtils.NO_AUTHORITIES;

/**
 * @author 应卓
 * @since 1.10.2
 */
public class StringToken extends AbstractAuthenticationToken implements Token {

    private final UserDetails userDetails;
    private final String tokenValue;

    private static Collection<GrantedAuthority> getAuthorities(UserDetails userDetails) {
        if (userDetails == null) {
            return NO_AUTHORITIES;
        }
        final Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        return authorities == null ? NO_AUTHORITIES : Collections.unmodifiableCollection(authorities);
    }

    public StringToken(Object tokenValue) {
        this(null, tokenValue);
    }

    public StringToken(UserDetails userDetails, Object tokenValue) {
        super(getAuthorities(userDetails));
        this.userDetails = userDetails;
        this.tokenValue = tokenValue != null ? tokenValue.toString() : "";
        this.setAuthenticated(false);
    }

    @Override
    public Object getTokenValue() {
        return this.tokenValue;
    }

    @Override
    public UserDetails getUserDetails() {
        return this.userDetails;
    }

    @Override
    public Object getCredentials() {
        return tokenValue;
    }

    @Override
    public String toString() {
        return tokenValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        StringToken that = (StringToken) o;
        return tokenValue.equals(that.tokenValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), tokenValue);
    }

}
