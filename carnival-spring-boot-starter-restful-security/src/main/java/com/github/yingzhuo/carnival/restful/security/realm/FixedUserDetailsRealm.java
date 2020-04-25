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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;

import java.util.Objects;
import java.util.Optional;

/**
 * @author 应卓
 * @since 1.4.10
 */
@Slf4j
public class FixedUserDetailsRealm implements UserDetailsRealm, InitializingBean {

    private final UserDetails userDetails;
    private final int order;

    public FixedUserDetailsRealm(UserDetails userDetails) {
        this(userDetails, 0);
    }

    public FixedUserDetailsRealm(UserDetails userDetails, int order) {
        this.userDetails = Objects.requireNonNull(userDetails);
        this.order = order;
    }

    @Override
    public Optional<UserDetails> loadUserDetails(Token token) {
        return Optional.of(userDetails);
    }

    @Override
    public int getOrder() {
        return this.order;
    }

    @Override
    public void afterPropertiesSet() {
        log.warn("~~~~~~");
        log.warn("DO NOT use {} in your production environment.", getClass().getName());
        log.warn("~~~~~~");
    }

}
