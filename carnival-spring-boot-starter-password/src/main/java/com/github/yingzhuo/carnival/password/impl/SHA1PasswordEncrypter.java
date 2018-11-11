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

import com.github.yingzhuo.carnival.password.PasswordEncrypter;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author 应卓
 */
public class SHA1PasswordEncrypter implements PasswordEncrypter {

    private final String salt;

    public SHA1PasswordEncrypter() {
        this("");
    }

    public SHA1PasswordEncrypter(String salt) {
        this.salt = salt;
    }

    @Override
    public String encrypt(String password) {
        return DigestUtils.sha1Hex(salt + password);
    }

}
