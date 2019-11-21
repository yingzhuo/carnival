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
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * @author 应卓
 */
public class NonePasswordEncrypter implements PasswordEncrypter {

    @Override
    public String encrypt(String rawPassword, String leftSalt, String rightSalt) {
        Objects.requireNonNull(rawPassword);
        return rawPassword;
    }

    @Override
    public boolean matches(String rawPassword, String leftSalt, String rightSalt, String encryptedPassword) {
        return StringUtils.equals(rawPassword, encryptedPassword);
    }

    @Override
    public String encrypt(String rawPassword) {
        return rawPassword;
    }

    @Override
    public String encrypt(String rawPassword, String rightSalt) {
        return rawPassword;
    }

    @Override
    public boolean matches(String rawPassword, String encryptedPassword) {
        return StringUtils.equals(rawPassword, encryptedPassword);
    }

    @Override
    public boolean matches(String rawPassword, String rightSalt, String encryptedPassword) {
        return StringUtils.equals(rawPassword, encryptedPassword);
    }

    @Override
    public boolean notMatches(String rawPassword, String encryptedPassword) {
        return !StringUtils.equals(rawPassword, encryptedPassword);
    }

    @Override
    public boolean notMatches(String rawPassword, String rightSalt, String encryptedPassword) {
        return !StringUtils.equals(rawPassword, encryptedPassword);
    }

    @Override
    public boolean notMatches(String rawPassword, String leftSalt, String rightSalt, String encryptedPassword) {
        return !StringUtils.equals(rawPassword, encryptedPassword);
    }

}
