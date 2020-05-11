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

import com.github.yingzhuo.carnival.secret.AESUtils;

/**
 * @author 应卓
 * @since 1.6.5
 */
public class AESPasswordEncrypter extends AbstractPasswordEncrypter {

    private final String passphrase;

    public AESPasswordEncrypter() {
        this(AESPasswordEncrypter.class.getName());
    }

    public AESPasswordEncrypter(String passphrase) {
        this.passphrase = passphrase;
    }

    @Override
    public String doEncrypt(String rawPassword, String leftSalt, String rightSalt) {
        return AESUtils.encrypt(contact(rawPassword, leftSalt, rightSalt), passphrase);
    }

}
