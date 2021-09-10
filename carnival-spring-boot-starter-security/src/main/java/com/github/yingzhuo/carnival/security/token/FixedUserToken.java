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

/**
 * @author 应卓
 * @since 1.10.17
 */
public final class FixedUserToken extends AbstractAuthenticationToken implements Token {

    private final UserDetails userDetails;

    public FixedUserToken(UserDetails userDetails) {
        super(userDetails.getAuthorities());
        this.userDetails = userDetails;
        super.setAuthenticated(true);
        super.setDetails(null);
    }

    @Override
    public final Object getCredentials() {
        return FixedUserToken.class.getName();
    }

    @Override
    public final Object getPrincipal() {
        return userDetails;
    }

    @Override
    public String getKey() {
        return "Fixed Token";
    }

    @Override
    public UserDetails getUserDetails() {
        return userDetails;
    }

    @Override
    public String toString() {
        return userDetails != null ? userDetails.toString() : "null";
    }

}
