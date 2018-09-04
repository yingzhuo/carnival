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

/**
 * @author 应卓
 */
public final class WebSecretHolder {

    static final ThreadLocal<String> nonceHolder = ThreadLocal.withInitial(() -> null);
    static final ThreadLocal<String> clientIdHolder = ThreadLocal.withInitial(() -> null);
    static final ThreadLocal<String> signatureHolder = ThreadLocal.withInitial(() -> null);
    static final ThreadLocal<String> timestampHolder = ThreadLocal.withInitial(() -> null);

    public static String getNonce() {
        return nonceHolder.get();
    }

    public static String getClientId() {
        return clientIdHolder.get();
    }

    public static String getSignature() {
        return signatureHolder.get();
    }

    public static String getTimestamp() {
        return timestampHolder.get();
    }

}
