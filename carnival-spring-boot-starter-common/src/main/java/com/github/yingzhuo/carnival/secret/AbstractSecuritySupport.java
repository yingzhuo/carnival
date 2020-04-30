/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.secret;

import java.util.Base64;

/**
 * @author 应卓
 * @since 1.6.1
 */
public abstract class AbstractSecuritySupport {

    protected static final String RSA = "RSA";
    protected static final String MD5withRSA = "MD5withRSA";

    protected static byte[] decryptBase64(String key) {
        return Base64.getDecoder().decode(key);
    }

    protected static String encryptBase64(byte[] key) {
        return new String(Base64.getEncoder().encode(key));
    }

}
