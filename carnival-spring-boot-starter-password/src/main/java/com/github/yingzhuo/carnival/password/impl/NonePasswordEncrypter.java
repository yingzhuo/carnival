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

import java.util.Objects;

/**
 * @author 应卓
 */
public class NonePasswordEncrypter implements PasswordEncrypter {

    @Override
    public String encrypt(String password) {
        return password;
    }

    @Override
    public boolean matches(String password, String encrypted) {
        return Objects.equals(password, encrypted);
    }

}
