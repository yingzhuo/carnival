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

import java.util.Objects;

/**
 * @author 应卓
 */
public interface PasswordEncrypter {

    public String encrypt(String password);

    public default boolean matches(String password, String encrypted) {
        Objects.requireNonNull(password);
        Objects.requireNonNull(encrypted);
        return encrypt(password).equals(encrypted);
    }

    public default boolean notMatches(String password, String encrypted) {
        return !matches(password, encrypted);
    }

    public default boolean matchesIgnoreCases(String password, String encrypted) {
        Objects.requireNonNull(password);
        Objects.requireNonNull(encrypted);
        return encrypt(password).equalsIgnoreCase(encrypted);
    }

    public default boolean notMatchesIgnoreCases(String password, String encrypted) {
        return !matchesIgnoreCases(password, encrypted);
    }

}
