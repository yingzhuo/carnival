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
import lombok.Getter;
import lombok.Setter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 应卓
 * @since 1.7.8
 */
final class ContextHolder {

    static final ThreadLocal<RestfulSecurityContext> holder = ThreadLocal.withInitial(() -> null);

    static void clear() {
        holder.remove();
    }

    static void set(HttpServletRequest request) {
        ContextImpl impl = (ContextImpl) holder.get();
        if (impl == null) {
            impl = new ContextImpl();
        }
        impl.setRequest(request);
        holder.set(impl);
    }

    static void set(HttpServletResponse response) {
        ContextImpl impl = (ContextImpl) holder.get();
        if (impl == null) {
            impl = new ContextImpl();
        }
        impl.setResponse(response);
        holder.set(impl);
    }

    static void set(Token token) {
        ContextImpl impl = (ContextImpl) holder.get();
        if (impl == null) {
            impl = new ContextImpl();
        }
        impl.setToken(token);
        holder.set(impl);
    }

    static void set(UserDetails ud) {
        ContextImpl impl = (ContextImpl) holder.get();
        if (impl == null) {
            impl = new ContextImpl();
        }
        impl.setUserDetails(ud);
        holder.set(impl);
    }

    @Getter
    @Setter
    static final class ContextImpl implements RestfulSecurityContext {
        private Token token;
        private UserDetails userDetails;
        private HttpServletRequest request;
        private HttpServletResponse response;

        // alias
        public Token getT() {
            return getToken();
        }

        // alias
        public UserDetails getU() {
            return getUserDetails();
        }
    }

}
