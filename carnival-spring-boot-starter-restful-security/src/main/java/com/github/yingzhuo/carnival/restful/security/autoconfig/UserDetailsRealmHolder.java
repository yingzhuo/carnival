/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.security.autoconfig;

import com.github.yingzhuo.carnival.restful.security.realm.UserDetailsRealm;

/**
 * @author 应卓
 * @since 1.4.0
 */
public final class UserDetailsRealmHolder {

    static UserDetailsRealm userDetailsRealm = null;

    public static UserDetailsRealm get() {
        return userDetailsRealm;
    }

    private UserDetailsRealmHolder() {
    }
}
