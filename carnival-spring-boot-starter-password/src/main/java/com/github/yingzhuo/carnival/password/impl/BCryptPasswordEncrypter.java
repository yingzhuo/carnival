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
    public String encrypt(String rawPassword) {
        Objects.requireNonNull(rawPassword);
        return BCrypt.hashpw(rawPassword, BCrypt.gensalt(13));
    }

    @Override
    public String encrypt(String rawPassword, String rightSalt) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String encrypt(String rawPassword, String leftSalt, String rightSalt) {
        throw new UnsupportedOperationException();
    }

    // -----------------------------------------------------------------------------------------------------------------

    @Override
    public boolean matches(String rawPassword, String encryptedPassword) {
        try {
            return BCrypt.checkpw(rawPassword, encryptedPassword);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean matches(String rawPassword, String rightSalt, String encryptedPassword) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean matches(String rawPassword, String leftSalt, String rightSalt, String encryptedPassword) {
        throw new UnsupportedOperationException();
    }

    // -----------------------------------------------------------------------------------------------------------------

    @Override
    public boolean notMatches(String rawPassword, String encryptedPassword) {
        return !matches(rawPassword, encryptedPassword);
    }

    @Override
    public boolean notMatches(String rawPassword, String leftSalt, String rightSalt, String encryptedPassword) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean notMatches(String rawPassword, String rightSalt, String encryptedPassword) {
        throw new UnsupportedOperationException();
    }

}
