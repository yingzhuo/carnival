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
import com.github.yingzhuo.carnival.restful.security.parser.TokenParser;
import com.github.yingzhuo.carnival.restful.security.realm.UserDetailsRealm;
import com.github.yingzhuo.carnival.restful.security.whitelist.TokenWhitelistManager;
import org.springframework.core.Ordered;

/**
 * @author 应卓
 * @since 1.6.5
 */
public interface RestfulSecurityConfigurer extends Ordered {

    @Override
    public default int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 1000;
    }

    public default String[] getPathPatterns() {
        return null;
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

}
