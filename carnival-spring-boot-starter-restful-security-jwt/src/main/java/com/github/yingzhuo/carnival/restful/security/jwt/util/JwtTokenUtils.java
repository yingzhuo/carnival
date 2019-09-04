/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.security.jwt.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.github.yingzhuo.carnival.restful.security.jwt.props.JwtProps;
import com.github.yingzhuo.carnival.spring.SpringUtils;

/**
 * @author 应卓
 * @since 1.1.7
 */
public final class JwtTokenUtils {

    private JwtTokenUtils() {
    }

    public static boolean isValidToken(String tokenValue) {
        try {
            JWTVerifier verifier = JWT.require(SpringUtils.getBean(JwtProps.class).getAlgorithm()).build();
            verifier.verify(tokenValue);
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

}
