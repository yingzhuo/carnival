/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.jwt.validating;

import com.github.yingzhuo.carnival.jwt.exception.InvalidTokenException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.web.context.request.WebRequest;

import java.lang.reflect.Method;

/**
 * @author 应卓
 */
@FunctionalInterface
public interface AuthorizationManager {

    public static final AuthorizationManager NOP = (jwt, request, method) -> {};

    public void check(DecodedJWT jwt, WebRequest webRequest, Method method) throws InvalidTokenException;

}
