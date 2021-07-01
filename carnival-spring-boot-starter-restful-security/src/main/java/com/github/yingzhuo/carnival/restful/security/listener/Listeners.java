/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.security.listener;

import com.github.yingzhuo.carnival.restful.security.token.Token;
import com.github.yingzhuo.carnival.restful.security.userdetails.UserDetails;
import org.springframework.core.OrderComparator;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author 应卓
 * @since 1.9.8
 */
public final class Listeners {

    private Listeners() {
    }

    public static Listener nop() {
        return (request, token, userDetails) -> {
        };
    }

    public static Listener chain(Listener... listeners) {
        final List<Listener> list = new ArrayList<>();
        Collections.addAll(list, listeners);
        OrderComparator.sort(list);
        return new ListenerChain(list);
    }

    // -----------------------------------------------------------------------------------------------------------------

    private static class ListenerChain implements Listener {
        private final List<Listener> listeners;

        public ListenerChain(List<Listener> listeners) {
            this.listeners = listeners;
        }

        @Override
        public void afterUserDetailsLoaded(NativeWebRequest request, Token token, UserDetails userDetails) {
            for (Listener listener : listeners) {
                listener.afterUserDetailsLoaded(request, token, userDetails);
            }
        }
    }

}
