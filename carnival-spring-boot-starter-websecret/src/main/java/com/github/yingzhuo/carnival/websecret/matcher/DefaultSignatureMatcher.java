/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.websecret.matcher;

import com.github.yingzhuo.carnival.websecret.util.SignatureUtils;

import java.util.Objects;

/**
 * @author 应卓
 */
public class DefaultSignatureMatcher implements SignatureMatcher {

    @Override
    public boolean matches(String signature, String secret, String nonce, String timestamp) {
        Objects.requireNonNull(signature);
        Objects.requireNonNull(secret);
        Objects.requireNonNull(nonce);
        Objects.requireNonNull(timestamp);
        return signature.equals(SignatureUtils.create(secret, nonce, timestamp));
    }

}
