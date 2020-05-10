/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.security.realm.x;

import com.github.yingzhuo.carnival.restful.security.exception.RestfulSecurityException;
import com.github.yingzhuo.carnival.restful.security.token.Token;
import com.github.yingzhuo.carnival.restful.security.userdetails.UserDetails;
import lombok.val;
import org.springframework.beans.factory.InitializingBean;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author 应卓
 * @since 1.6.5
 */
public class CompositeExtraUserDetailsRealm implements ExtraUserDetailsRealm, InitializingBean {

    private final List<ExtraUserDetailsRealm> realms;

    public CompositeExtraUserDetailsRealm(List<ExtraUserDetailsRealm> realms) {
        this.realms = Collections.unmodifiableList(realms);
    }

    public CompositeExtraUserDetailsRealm(ExtraUserDetailsRealm... realms) {
        this.realms = Collections.unmodifiableList(Arrays.asList(realms));
    }

    @Override
    public void execute(Token token, UserDetails userDetails) throws RestfulSecurityException {
        if (realms == null || realms.isEmpty()) {
            return;
        }

        for (val realm : realms) {
            realm.execute(token, userDetails);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (realms != null) {
            for (val realm : realms) {
                if (realm instanceof InitializingBean) {
                    ((InitializingBean) realm).afterPropertiesSet();
                }
            }
        }
    }

    public List<ExtraUserDetailsRealm> getRealms() {
        return realms;
    }
}
