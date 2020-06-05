/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.mvc.client;

/**
 * @author 应卓
 * @since 1.6.14
 */
public final class ClientInfoContext {

    private static final ThreadLocal<ClientOSType> CLIENT_OS_TYPE_HOLDER = ThreadLocal.withInitial(() -> null);
    private static final ThreadLocal<String> CLIENT_OS_VERSION_HOLDER = ThreadLocal.withInitial(() -> null);
    private static final ThreadLocal<String> CLIENT_APP_VERSION_HOLDER = ThreadLocal.withInitial(() -> null);
    private static final ThreadLocal<String> CLIENT_USING_BACKEND_VERSION_HOLDER = ThreadLocal.withInitial(() -> null);
    private ClientInfoContext() {
    }

    static void setClientOsType(ClientOSType type) {
        CLIENT_OS_TYPE_HOLDER.set(type);
    }

    static void setClientOsVersion(String version) {
        CLIENT_OS_VERSION_HOLDER.set(version);
    }

    static void clean() {
        CLIENT_OS_TYPE_HOLDER.remove();
        CLIENT_OS_VERSION_HOLDER.remove();
        CLIENT_APP_VERSION_HOLDER.remove();
        CLIENT_USING_BACKEND_VERSION_HOLDER.remove();
    }

    public static ClientOSType getClientOSType() {
        return CLIENT_OS_TYPE_HOLDER.get();
    }

    public static String getClientOSVersion() {
        return CLIENT_OS_VERSION_HOLDER.get();
    }

    public static String getClientAppVersion() {
        return CLIENT_APP_VERSION_HOLDER.get();
    }

    static void setClientAppVersion(String version) {
        CLIENT_APP_VERSION_HOLDER.set(version);
    }

    public static String getClientUsingBackendVersion() {
        return CLIENT_USING_BACKEND_VERSION_HOLDER.get();
    }

    static void setClientUsingBackendVersion(String version) {
        CLIENT_USING_BACKEND_VERSION_HOLDER.set(version);
    }

}
