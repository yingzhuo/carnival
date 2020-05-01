/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.mvc.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 应卓
 * @since 1.6.2
 */
public final class VeryFirstServletContext {

    private static final ThreadLocal<HttpServletRequest> REQUEST_HOLDER = ThreadLocal.withInitial(() -> null);
    private static final ThreadLocal<HttpServletResponse> RESPONSE_HOLDER = ThreadLocal.withInitial(() -> null);

    public VeryFirstServletContext() {
    }

    static void setRequest(HttpServletRequest request) {
        REQUEST_HOLDER.set(request);
    }

    static void setResponse(HttpServletResponse response) {
        RESPONSE_HOLDER.set(response);
    }

    public static HttpServletRequest getRequest() {
        return REQUEST_HOLDER.get();
    }

    public static HttpServletResponse getResponse() {
        return RESPONSE_HOLDER.get();
    }

    static void clean() {
        REQUEST_HOLDER.remove();
        RESPONSE_HOLDER.remove();
    }

}
