/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.security.realm;

import com.github.yingzhuo.carnival.restful.security.exception.UnsupportedTokenTypeException;
import com.github.yingzhuo.carnival.restful.security.token.Token;
import com.github.yingzhuo.carnival.restful.security.token.UsernamePasswordToken;
import com.github.yingzhuo.carnival.restful.security.userdetails.UserDetails;
import lombok.val;

import java.util.Optional;

/**
 * @author 应卓
 */
public abstract class AbstractUsernamePasswordUserDetailsRealm implements UserDetailsRealm {

    @Override
    public final Optional<UserDetails> loadUserDetails(Token token) {

        if (token instanceof UsernamePasswordToken) {
            val username = ((UsernamePasswordToken) token).getUsername();
            val password = ((UsernamePasswordToken) token).getPassword();
            return Optional.ofNullable(doLoadUserDetails(username, password));
        }

        throw new UnsupportedTokenTypeException();
    }

    protected abstract UserDetails doLoadUserDetails(String username, String password);

}
