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

import org.springframework.web.context.request.NativeWebRequest;

/**
 * @author 应卓
 * @since 1.6.14
 */
public interface ClientInfoResolver extends
        ClientOSTypeResolver,
        ClientOSVersionResolver,
        ClientAppVersionResolver,
        ClientUsingBackendVersionResolver {

    public static final ClientInfoResolver DEFAULT = new ClientInfoResolver() {
    };

    @Override
    public default ClientOSType resolveClientOSType(NativeWebRequest request) {
        String s = request.getHeader("X-Client-OS-Type");
        if (s == null) return null;

        if (s.equalsIgnoreCase("ANDROID")) {
            return ClientOSType.ANDROID;
        }

        if (s.equalsIgnoreCase("IOS")) {
            return ClientOSType.IOS;
        }

        return null;
    }

    @Override
    public default String resolveClientOSVersion(NativeWebRequest request) {
        return request.getHeader("X-Client-OS-Version");
    }

    @Override
    public default String resolveClientAppVersion(NativeWebRequest request) {
        return request.getHeader("X-Client-App-Version");
    }

    @Override
    public default String resolveClientUsingBackendVersion(NativeWebRequest request) {
        return request.getHeader("X-Client-Using-Backend-Version");
    }

}
