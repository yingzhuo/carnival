/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.security.params.core;

import com.github.yingzhuo.carnival.restful.security.params.Params;

/**
 * @author 应卓
 * @since 1.6.30
 */
public final class ParamsValidatingContext {

    private ParamsValidatingContext() {
    }

    private static final ThreadLocal<Params> HOLDER = new ThreadLocal<>();

    static void set(Params params) {
        HOLDER.set(params);
    }

    public static Params get() {
        return HOLDER.get();
    }

    static void remove() {
        HOLDER.remove();
    }

}
