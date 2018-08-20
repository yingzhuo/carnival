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
import com.github.yingzhuo.carnival.password.support.AESEncrypter;

/**
 * @author 应卓
 */
public class AesPasswordEncrypter implements PasswordEncrypter {

    private static final String DEFAULT_PASS = AesPasswordEncrypter.class.getName();

    @Override
    public String encrypt(String password) {
        return new AESEncrypter(DEFAULT_PASS).encrypt(password);
    }

}
