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
import java.util.function.Function;

/**
 * @author 应卓
 */
@FunctionalInterface
public interface PasswordEncrypter extends Function<String, String> {

    public String encrypt(String password);

    @Override
    default String apply(String s) {
        return encrypt(s);
    }

    public default boolean matches(String password, String encrypted) {
        Objects.requireNonNull(password);
        Objects.requireNonNull(encrypted);
        return encrypt(password).equals(encrypted);
    }

    public default boolean notMatches(String password, String encrypted) {
        return !matches(password, encrypted);
    }

}
