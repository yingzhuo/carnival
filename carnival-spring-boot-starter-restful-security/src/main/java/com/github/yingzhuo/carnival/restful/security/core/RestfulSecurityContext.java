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

import com.github.yingzhuo.carnival.restful.security.SkipReason;
import com.github.yingzhuo.carnival.restful.security.token.Token;
import com.github.yingzhuo.carnival.restful.security.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * 安全上下文
 *
 * @author 应卓
 */
public final class RestfulSecurityContext {

    private final static ThreadLocal<Token> tokenHolder = ThreadLocal.withInitial(() -> null);
    private final static ThreadLocal<UserDetails> userDetailsHolder = ThreadLocal.withInitial(() -> null);
    private final static ThreadLocal<SkipReason> skipReasonHolder = ThreadLocal.withInitial(() -> null);
    private final static ThreadLocal<HttpServletRequest> requestHolder = ThreadLocal.withInitial(() -> null);
    private final static ThreadLocal<HttpServletResponse> responseHolder = ThreadLocal.withInitial(() -> null);

    private RestfulSecurityContext() {
    }

    public static Optional<Token> getToken() {
        return Optional.ofNullable(tokenHolder.get());
    }

    static void setToken(Token token) {
        tokenHolder.set(token);
    }

    public static Optional<UserDetails> getUserDetails() {
        return Optional.ofNullable(userDetailsHolder.get());
    }

    static void setUserDetails(UserDetails userDetails) {
        userDetailsHolder.set(userDetails);
    }

    public static Optional<SkipReason> getSkipReason() {
        return Optional.ofNullable(skipReasonHolder.get());
    }

    static void setSkipReason(SkipReason reason) {
        skipReasonHolder.set(reason);
    }

    public static HttpServletRequest getRequest() {
        return requestHolder.get();
    }

    static void setRequest(HttpServletRequest request) {
        requestHolder.set(request);
    }

    public static HttpServletResponse getResponse() {
        return responseHolder.get();
    }

    static void setResponse(HttpServletResponse response) {
        responseHolder.set(response);
    }

    static void clean() {
        tokenHolder.remove();
        userDetailsHolder.remove();
        skipReasonHolder.remove();
        requestHolder.remove();
        responseHolder.remove();
    }

}
