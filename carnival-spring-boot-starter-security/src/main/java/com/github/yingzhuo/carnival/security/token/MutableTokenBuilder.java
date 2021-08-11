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

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;

/**
 * @author 应卓
 * @since 1.10.6
 */
public class MutableTokenBuilder {

    private UserDetails userDetails = null;
    private String key = null;
    private Object details = null;
    private long creationTime = System.currentTimeMillis();
    private String extendedInformation = null;
    private boolean authenticated = false;
    MutableTokenBuilder() {
    }

    public MutableTokenBuilder userDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
        return this;
    }

    public MutableTokenBuilder key(String key) {
        this.key = key;
        return this;
    }

    public MutableTokenBuilder details(Object details) {
        this.details = details;
        return this;
    }

    public MutableTokenBuilder creationTime(Date creationTime) {
        this.creationTime = creationTime.getTime();
        return this;
    }

    public MutableTokenBuilder creationTime(long creationTime) {
        this.creationTime = creationTime;
        return this;
    }

    public MutableTokenBuilder extendedInformation(String extendedInformation) {
        this.extendedInformation = extendedInformation;
        return this;
    }

    public MutableTokenBuilder authenticated(boolean authenticated) {
        this.authenticated = authenticated;
        return this;
    }

    public MutableToken build() {
        final MutableToken token = new MutableToken();
        token.setUserDetails(this.userDetails);
        token.setKey(this.key);
        token.setDetails(this.details);
        token.setExtendedInformation(this.extendedInformation);
        token.setAuthenticated(this.authenticated);
        token.setCreationTime(this.creationTime);
        return token;
    }

}
