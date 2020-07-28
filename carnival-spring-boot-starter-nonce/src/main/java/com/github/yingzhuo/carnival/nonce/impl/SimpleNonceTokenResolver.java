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
public class SimpleNonceTokenResolver implements NonceTokenResolver {

    @Override
    public NonceToken resolve(HttpServletRequest request) {
        String value = request.getParameter("_nonce_token");
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        return new StringNonceToken(value);
    }

}
