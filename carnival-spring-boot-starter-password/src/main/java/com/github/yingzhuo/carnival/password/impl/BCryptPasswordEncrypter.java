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
import com.github.yingzhuo.carnival.password.support.BCrypt;

import java.util.Objects;

/**
 * @author 应卓
 */
public class BCryptPasswordEncrypter implements PasswordEncrypter {

    @Override
    public String encrypt(String password) {
        Objects.requireNonNull(password);
        return BCrypt.hashpw(password, BCrypt.gensalt(13));
    }

    @Override
    public boolean matches(String password, String encrypted) {
        try {
            return BCrypt.checkpw(password, encrypted);
        } catch (Exception e) {
            return false;
        }
    }

}
