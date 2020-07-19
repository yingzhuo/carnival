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
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 桥接SpringSecurity的{@link PasswordEncoder}
 *
 * @author 应卓
 * @since 1.6.27
 */
public class PasswordEncoderEncrypter implements PasswordEncrypter {

    private final PasswordEncoder passwordEncoder;

    public PasswordEncoderEncrypter(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String encrypt(String rawPassword, String leftSalt, String rightSalt) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String encrypt(String rawPassword) {
        return this.passwordEncoder.encode(rawPassword);
    }

    @Override
    public String encrypt(String rawPassword, String rightSalt) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean matches(String rawPassword, String encryptedPassword) {
        return this.passwordEncoder.matches(rawPassword, encryptedPassword);
    }

    @Override
    public boolean matches(String rawPassword, String leftSalt, String rightSalt, String encryptedPassword) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean matches(String rawPassword, String rightSalt, String encryptedPassword) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean notMatches(String rawPassword, String encryptedPassword) {
        return !this.passwordEncoder.matches(rawPassword, encryptedPassword);
    }

    @Override
    public boolean notMatches(String rawPassword, String rightSalt, String encryptedPassword) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean notMatches(String rawPassword, String leftSalt, String rightSalt, String encryptedPassword) {
        throw new UnsupportedOperationException();
    }

}
