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
public class RepeatPasswordEncrypter implements PasswordEncrypter {

    private final PasswordEncrypter delegate;
    private final int repeat;

    public RepeatPasswordEncrypter(PasswordEncrypter delegate, int repeat) {
        this.delegate = Objects.requireNonNull(delegate);
        this.repeat = repeat <= 0 ? 1 : repeat;
    }

    @Override
    public String encrypt(String rawPassword, String leftSalt, String rightSalt) {

        String s = rawPassword;

        for (int i = 0; i < repeat; i++) {
            s = delegate.encrypt(s, leftSalt, rightSalt);
        }

        return s;
    }

}
