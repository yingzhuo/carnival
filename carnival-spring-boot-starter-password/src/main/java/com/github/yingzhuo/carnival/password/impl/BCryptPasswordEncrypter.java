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
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * @author 应卓
 */
@Slf4j
public class BCryptPasswordEncrypter implements PasswordEncrypter {

    private final String bcryptSalt = BCrypt.gensalt();

    @Override
    public String encrypt(String rawPassword) {
        Objects.requireNonNull(rawPassword);
        return BCrypt.hashpw(rawPassword, bcryptSalt);
    }

    @Override
    public String encrypt(String rawPassword, String rightSalt) {
        log.warn("salt is ignored.");
        return encrypt(rawPassword);
    }

    @Override
    public String encrypt(String rawPassword, String leftSalt, String rightSalt) {
        log.warn("salt is ignored.");
        return encrypt(rawPassword);
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
    public boolean matches(String rawPassword, String leftSalt, String rightSalt, String encryptedPassword) {
        log.warn("salt is ignored.");
        return matches(rawPassword, encryptedPassword);
    }

    @Override
    public boolean matches(String rawPassword, String rightSalt, String encryptedPassword) {
        log.warn("salt is ignored.");
        return matches(rawPassword, encryptedPassword);
    }

    @Override
    public boolean notMatches(String rawPassword, String encryptedPassword) {
        log.warn("salt is ignored.");
        return !matches(rawPassword, encryptedPassword);
    }

    @Override
    public boolean notMatches(String rawPassword, String rightSalt, String encryptedPassword) {
        log.warn("salt is ignored.");
        return !matches(rawPassword, encryptedPassword);
    }

    @Override
    public boolean notMatches(String rawPassword, String leftSalt, String rightSalt, String encryptedPassword) {
        log.warn("salt is ignored.");
        return !matches(rawPassword, encryptedPassword);
    }

}
