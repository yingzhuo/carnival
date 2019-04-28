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

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author 应卓
 */
public class JwtToken implements Authentication, Serializable {

    private final String rawToken;
    private boolean authenticated = false;
    private Set<GrantedAuthority> authorities = new HashSet<>();
    private Object details;

    public JwtToken(String rawToken) {
        Assert.hasText(rawToken, () -> null);
        this.rawToken = rawToken;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public Object getDetails() {
        return this.details;
    }

    @Override
    public boolean isAuthenticated() {
        return this.authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.authenticated = isAuthenticated;
    }

    @Override
    public Object getCredentials() {
        throw new UnsupportedOperationException();      // jwt 无密码概念
    }

    @Override
    public Object getPrincipal() {
        throw new UnsupportedOperationException();      // jwt 无用户名概念
    }

    @Override
    public String getName() {
        throw new UnsupportedOperationException();      // jwt 无用户概念
    }

    // -------------------------------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return rawToken;
    }

    // -------------------------------------------------------------------------------------------------------------

    public String getRawToken() {
        return rawToken;
    }

    public void setAuthorities(Set<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public void setDetails(Object details) {
        this.details = details;
    }
}
