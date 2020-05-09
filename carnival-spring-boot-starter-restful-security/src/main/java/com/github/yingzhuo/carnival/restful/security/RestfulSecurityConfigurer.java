/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.security;

import com.github.yingzhuo.carnival.restful.security.blacklist.TokenBlacklistManager;
import com.github.yingzhuo.carnival.restful.security.exception.ExceptionTransformer;
import com.github.yingzhuo.carnival.restful.security.parser.TokenParser;
import com.github.yingzhuo.carnival.restful.security.realm.UserDetailsRealm;
import com.github.yingzhuo.carnival.restful.security.realm.x.ExtraUserDetailsRealm;
import com.github.yingzhuo.carnival.restful.security.whitelist.TokenWhitelistManager;

/**
 * @author 应卓
 * @since 1.6.5
 */
public interface RestfulSecurityConfigurer {

    public default int getOrder() {
        return 0;
    }

    public default AuthenticationStrategy getAuthenticationStrategy() {
        return AuthenticationStrategy.ANNOTATED_REQUESTS;
    }

    public default TokenParser getTokenParser() {
        return null;
    }

    public default UserDetailsRealm getUserDetailsRealm() {
        return null;
    }

    public default TokenBlacklistManager getTokenBlacklistManager() {
        return null;
    }

    public default TokenWhitelistManager getTokenWhitelistManager() {
        return null;
    }

    public default ExtraUserDetailsRealm getExtraUserDetailsRealm() {
        return null;
    }

    public default ExceptionTransformer getExceptionTransformer() {
        return null;
    }

}
