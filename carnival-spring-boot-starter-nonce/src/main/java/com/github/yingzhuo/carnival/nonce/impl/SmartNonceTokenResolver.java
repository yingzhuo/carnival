/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.nonce.impl;

import com.github.yingzhuo.carnival.nonce.NonceToken;
import com.github.yingzhuo.carnival.nonce.NonceTokenResolver;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 应卓
 * @since 1.6.29
 */
public class SmartNonceTokenResolver implements NonceTokenResolver {

    private final String parameterName;
    private final String headerName;

    public SmartNonceTokenResolver() {
        this("_nonce_token", "X-Nonce-Token");
    }

    public SmartNonceTokenResolver(String parameterName, String headerName) {
        this.parameterName = parameterName;
        this.headerName = headerName;
    }

    @Override
    public NonceToken resolve(HttpServletRequest request) {
        String value = request.getParameter(parameterName);

        // from parameter
        if (value != null && !value.trim().isEmpty()) {
            return new StringNonceToken(value);
        }

        // from header
        value = request.getHeader(headerName);
        if (value != null && !value.trim().isEmpty()) {
            return new StringNonceToken(value);
        }

        return null;
    }

}
