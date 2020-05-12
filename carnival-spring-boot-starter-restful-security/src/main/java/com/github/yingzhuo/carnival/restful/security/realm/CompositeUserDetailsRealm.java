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
import org.springframework.beans.factory.InitializingBean;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author 应卓
 * @since 1.4.8
 */
public class CompositeUserDetailsRealm implements UserDetailsRealm, InitializingBean {

    private final List<UserDetailsRealm> realms;

    public CompositeUserDetailsRealm(List<UserDetailsRealm> realms) {
        this.realms = Collections.unmodifiableList(realms);
    }

    public CompositeUserDetailsRealm(UserDetailsRealm... realms) {
        this.realms = Collections.unmodifiableList(Arrays.asList(realms));
    }

    public static CompositeUserDetailsRealm of(UserDetailsRealm... realms) {
        return new CompositeUserDetailsRealm(realms);
    }

    @Override
    public Optional<UserDetails> loadUserDetails(Token token) {
        for (val realm : realms) {
            val op = realm.loadUserDetails(token);
            if (op.isPresent()) {
                return op;
            }
        }
        return Optional.empty();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        for (UserDetailsRealm realm : this.realms) {
            if (realm instanceof InitializingBean) {
                ((InitializingBean) realm).afterPropertiesSet();
            }
        }
    }

    public List<UserDetailsRealm> getRealms() {
        return realms;
    }

}
