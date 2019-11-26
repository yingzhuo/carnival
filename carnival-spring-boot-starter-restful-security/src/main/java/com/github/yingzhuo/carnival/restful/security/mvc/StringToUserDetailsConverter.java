/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.security.mvc;

import com.github.yingzhuo.carnival.restful.security.core.RestfulSecurityContext;
import com.github.yingzhuo.carnival.restful.security.exception.UnsupportedTokenTypeException;
import com.github.yingzhuo.carnival.restful.security.realm.UserDetailsRealm;
import com.github.yingzhuo.carnival.restful.security.token.StringToken;
import com.github.yingzhuo.carnival.restful.security.token.Token;
import com.github.yingzhuo.carnival.restful.security.userdetails.UserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;

/**
 * @author 应卓
 * @since 1.3.0
 */
@Slf4j
public class StringToUserDetailsConverter implements Converter<String, UserDetails> {

    @Override
    public UserDetails convert(String tokenString) {

        if (tokenString == null) {
            return null;
        }

        final UserDetailsRealm realm = RestfulSecurityContext.getFinalUserDetailsRealm();

        if (realm == null) {
            return null;
        }

        try {
            Token token = StringToken.of(tokenString);
            return realm.loadUserDetails(token).orElse(null);
        } catch (UnsupportedTokenTypeException e) {
            log.warn("string token is NOT supported.");
            return null;
        } catch (Exception e) {
            return null;
        }
    }

}
