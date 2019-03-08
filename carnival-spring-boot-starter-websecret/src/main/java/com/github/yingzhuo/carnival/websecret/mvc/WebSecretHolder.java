/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.websecret.mvc;

import com.github.yingzhuo.carnival.websecret.WebSecretContext;

/**
 * @author 应卓
 */
public final class WebSecretHolder {

    private WebSecretHolder() {
    }

    static final ThreadLocal<WebSecretContext> holder = ThreadLocal.withInitial(WebSecretContext.Impl::new);

    public static WebSecretContext get() {
        return holder.get();
    }

    public static String getNonce() {
        return get().getNonce();
    }

    public static String getClientId() {
        return get().getClientId();
    }

    public static String getSignature() {
        return get().getSignature();
    }

    public static String getTimestamp() {
        return get().getTimestamp();
    }

    public static String getSecret() {
        return get().getSecret();
    }

    static void setNonce(String v) {
        ((WebSecretContext.Impl) holder.get()).setNonce(v);
    }

    static void setClientId(String v) {
        ((WebSecretContext.Impl) holder.get()).setClientId(v);
    }

    static void setSignature(String v) {
        ((WebSecretContext.Impl) holder.get()).setSignature(v);
    }

    static void setTimestamp(String v) {
        ((WebSecretContext.Impl) holder.get()).setTimestamp(v);
    }

    static void setSecret(String v) {
        ((WebSecretContext.Impl) holder.get()).setSecret(v);
    }

    static void clean() {
        holder.set(new WebSecretContext.Impl());
    }

}
