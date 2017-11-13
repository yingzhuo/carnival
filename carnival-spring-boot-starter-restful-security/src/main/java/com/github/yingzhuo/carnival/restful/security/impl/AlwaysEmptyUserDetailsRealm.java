/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.security.impl;

import com.github.yingzhuo.carnival.restful.security.Token;
import com.github.yingzhuo.carnival.restful.security.UserDetails;
import com.github.yingzhuo.carnival.restful.security.UserDetailsRealm;

import java.util.Optional;

public final class AlwaysEmptyUserDetailsRealm implements UserDetailsRealm {

    @Override
    public Optional<UserDetails> loadUserDetails(Token token) {
        return Optional.empty();
    }

}
