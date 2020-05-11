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
 * @since 1.6.6
 */
abstract class AbstractPasswordEncrypter implements PasswordEncrypter {

    protected final String nullToEmpty(String s) {
        return s != null ? s : EMPTY;
    }

    @Override
    public final String encrypt(String rawPassword, String leftSalt, String rightSalt) {
        if (rawPassword == null) {
            throw new IllegalArgumentException("rawPassword is null.");
        }

        leftSalt = nullToEmpty(leftSalt);
        rightSalt = nullToEmpty(rightSalt);

        return doEncrypt(rawPassword, leftSalt, rightSalt);
    }

    protected abstract String doEncrypt(String rawPassword, String leftSalt, String rightSalt);

    @Override
    public final boolean matches(String rawPassword, String leftSalt, String rightSalt, String encryptedPassword) {
        if (rawPassword == null || encryptedPassword == null) {
            return false;
        }

        leftSalt = nullToEmpty(leftSalt);
        rightSalt = nullToEmpty(rightSalt);

        return doMatches(rawPassword, leftSalt, rightSalt, encryptedPassword);
    }

    protected boolean doMatches(String rawPassword, String leftSalt, String rightSalt, String encryptedPassword) {
        String encrypted = this.encrypt(rawPassword, leftSalt, rightSalt);
        return Objects.equals(encrypted, encryptedPassword);
    }

    protected final String contact(String rawPassword, String leftSalt, String rightSalt) {
        return leftSalt + rawPassword + rightSalt;
    }

}
