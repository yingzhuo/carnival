/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.security.core;

import com.github.yingzhuo.carnival.restful.security.token.Token;
import com.github.yingzhuo.carnival.restful.security.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

/**
 * 安全上下文
 *
 * @author 应卓
 * @since 1.7.8
 */
public interface RestfulSecurityContext extends Serializable {

    public static RestfulSecurityContext current() {
        return ContextHolder.holder.get();
    }

    public Token getToken();

    public default String getTokenAsString() {
        final Token token = getToken();
        return token != null ? token.toString() : null;
    }

    public UserDetails getUserDetails();

    public HttpServletRequest getRequest();

    public HttpServletResponse getResponse();

}
