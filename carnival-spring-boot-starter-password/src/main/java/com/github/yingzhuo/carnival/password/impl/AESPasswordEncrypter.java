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
import com.github.yingzhuo.carnival.secret.AESUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * @author 应卓
 * @since 1.6.5
 */
public class AESPasswordEncrypter implements PasswordEncrypter {

    private final String passphrase;

    public AESPasswordEncrypter() {
        this(AESPasswordEncrypter.class.getName());
    }

    public AESPasswordEncrypter(String passphrase) {
        this.passphrase = passphrase;
    }

    @Override
    public String encrypt(String rawPassword, String leftSalt, String rightSalt) {
        Objects.requireNonNull(rawPassword);

        if (StringUtils.isBlank(leftSalt)) {
            leftSalt = EMPTY_SALT;
        }

        if (StringUtils.isBlank(rightSalt)) {
            rightSalt = EMPTY_SALT;
        }

        return AESUtils.encrypt(leftSalt + rawPassword + rightSalt, passphrase);
    }

}
