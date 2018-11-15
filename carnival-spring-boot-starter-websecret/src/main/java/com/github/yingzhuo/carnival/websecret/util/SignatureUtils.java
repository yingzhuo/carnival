/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.websecret.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author 应卓
 */
public final class SignatureUtils {

    private SignatureUtils() {
        super();
    }

    public static String create(String secret, String nonce, String timestamp) {
        return DigestUtils.sha256Hex(secret + nonce + timestamp);
    }

}
