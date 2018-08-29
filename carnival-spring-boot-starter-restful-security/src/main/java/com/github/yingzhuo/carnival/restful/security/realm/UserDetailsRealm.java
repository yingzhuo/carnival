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

import com.github.yingzhuo.carnival.restful.security.token.Token;
import com.github.yingzhuo.carnival.restful.security.userdetails.UserDetails;
import lombok.val;

import java.util.Objects;
import java.util.Optional;

/**
 * @author 应卓
 */
@FunctionalInterface
public interface UserDetailsRealm {

    public Optional<UserDetails> loadUserDetails(Token token);

    public default UserDetailsRealm chain(UserDetailsRealm that) {
        return token -> {
            val thisOp = this.loadUserDetails(token);

            if (thisOp.isPresent()) {
                return thisOp;
            } else {
                return Objects.requireNonNull(that).loadUserDetails(token);
            }
        };
    }

}
