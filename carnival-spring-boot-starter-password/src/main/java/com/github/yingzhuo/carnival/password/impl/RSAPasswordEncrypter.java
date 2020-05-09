/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.password.impl;

import com.github.yingzhuo.carnival.common.io.ResourceText;
import com.github.yingzhuo.carnival.password.PasswordEncrypter;
import com.github.yingzhuo.carnival.secret.rsa.RSAHelper;
import com.github.yingzhuo.carnival.secret.rsa.RSAKeyPair;
import lombok.val;

/**
 * @author 应卓
 * @since 1.6.5
 */
public class RSAPasswordEncrypter implements PasswordEncrypter {

    private final RSAKeyPair keyPair;

    public RSAPasswordEncrypter(ResourceText publicKey, ResourceText privateKey) {
        this(publicKey.getTextAsOneLine(), privateKey.getTextAsOneLine());
    }

    public RSAPasswordEncrypter(String publicKey, String privateKey) {
        this.keyPair = RSAKeyPair.fromString(publicKey, privateKey);
    }

    public RSAPasswordEncrypter(RSAKeyPair keyPair) {
        this.keyPair = keyPair;
    }

    public static RSAPasswordEncrypter predefined(int number) {
        if (number < 0 || number > 7) {
            throw new IllegalArgumentException("number range: [0,7]");
        }

        return new RSAPasswordEncrypter(
                ResourceText.of(String.format("classpath:com/github/yingzhuo/carnival/secret/predefined/rsa-%d-public", number)),
                ResourceText.of(String.format("classpath:com/github/yingzhuo/carnival/secret/predefined/rsa-%d-private", number))
        );
    }

    @Override
    public String encrypt(String rawPassword, String leftSalt, String rightSalt) {
        return RSAHelper.of(keyPair).encryptByPrivateKey(leftSalt + rawPassword + rightSalt);
    }

}
