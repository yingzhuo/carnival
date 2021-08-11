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

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

import static org.springframework.security.core.authority.AuthorityUtils.NO_AUTHORITIES;

/**
 * @author 应卓
 * @since 1.10.3
 */
public class MutableToken implements Token {

    private UserDetails userDetails;
    private String key;
    private Object details;
    private long creationTime = System.currentTimeMillis();
    private String extendedInformation;
    private boolean authenticated = false;

    public static MutableTokenBuilder builder() {
        return new MutableTokenBuilder();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (userDetails == null) {
            return NO_AUTHORITIES;
        }
        final Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        return authorities == null ? NO_AUTHORITIES : Collections.unmodifiableCollection(authorities);
    }

    @Override
    public String getName() {
        if (userDetails == null) {
            return "";
        }
        if (userDetails.getUsername() != null) {
            return userDetails.getUsername();
        }
        return "";
    }

    @Override
    public UserDetails getUserDetails() {
        return userDetails;
    }

    @Override
    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }

    @Override
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public Object getDetails() {
        return details;
    }

    public void setDetails(Object details) {
        this.details = details;
    }

    public long getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(long creationTime) {
        this.creationTime = creationTime;
    }

    @Override
    public String getExtendedInformation() {
        return extendedInformation;
    }

    public void setExtendedInformation(String extendedInformation) {
        this.extendedInformation = extendedInformation;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.authenticated = isAuthenticated;
    }

    @Override
    public Object getCredentials() {
        return key;
    }

    @Override
    public Object getPrincipal() {
        return userDetails;
    }

    @Override
    public long getKeyCreationTime() {
        return creationTime;
    }

    @Override
    public String toString() {
        return key;
    }

}
