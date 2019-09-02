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
import org.springframework.core.Ordered;

import java.util.Optional;

/**
 * @author 应卓
 * @see com.github.yingzhuo.carnival.restful.security.realm.UserDetailsRealm
 * @see com.github.yingzhuo.carnival.restful.security.parser.DebuggingTokenParser
 * @since 1.1.6
 */
public class DebuggingUserDetailsRealm implements UserDetailsRealm {

    private final UserDetails.Builder builder;

    public DebuggingUserDetailsRealm(UserDetails.Builder builder) {
        this.builder = builder;
    }

    @Override
    public Optional<UserDetails> loadUserDetails(Token token) {
        return builder != null ? Optional.of(builder.build()) : Optional.empty();
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }

}
