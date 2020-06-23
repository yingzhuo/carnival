/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.shield.algorithm;

import com.github.yingzhuo.carnival.secret.rsa.RSAHelper;
import com.github.yingzhuo.carnival.secret.rsa.RSAKeyPair;

/**
 * @author 应卓
 * @since 1.6.22
 */
public final class RSAAlgorithm implements Algorithm {

    private final RSAHelper helper;

    public RSAAlgorithm(String publicKey, String privateKey) {
        this.helper = RSAHelper.of(RSAKeyPair.fromString(publicKey, privateKey));
    }

    @Override
    public String encrypt(String string) {
        return helper.encryptByPrivateKey(string);
    }

    @Override
    public String decrypt(String string) {
        return helper.decryptByPublicKey(string);
    }

}
