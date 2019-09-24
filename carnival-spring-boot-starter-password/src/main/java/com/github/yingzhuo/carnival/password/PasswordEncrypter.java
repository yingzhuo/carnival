/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.password;

import org.apache.commons.lang3.StringUtils;

/**
 * @author 应卓
 */
public interface PasswordEncrypter {

    public static final String EMPTY_SALT = "";

    public String encrypt(String rawPassword, String leftSalt, String rightSalt);

    public default String encrypt(String rawPassword) {
        return encrypt(rawPassword, EMPTY_SALT, EMPTY_SALT);
    }

    public default boolean matches(String rawPassword, String encryptedPassword) {
        return matches(rawPassword, EMPTY_SALT, EMPTY_SALT, encryptedPassword);
    }

    public default boolean matches(String rawPassword, String leftSalt, String rightSalt, String encryptedPassword) {
        return StringUtils.equals(encrypt(rawPassword, leftSalt, rightSalt), encryptedPassword);
    }

    public default boolean notMatches(String rawPassword, String encryptedPassword) {
        return notMatches(rawPassword, EMPTY_SALT, EMPTY_SALT, encryptedPassword);
    }

    public default boolean notMatches(String rawPassword, String leftSalt, String rightSalt, String encryptedPassword) {
        return !matches(rawPassword, leftSalt, rightSalt, encryptedPassword);
    }

}
