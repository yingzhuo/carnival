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

import java.util.Arrays;

/**
 * @author 应卓
 * @since 1.6.27
 */
public interface PasswordEncoder extends org.springframework.security.crypto.password.PasswordEncoder {

    public default boolean notMatches(CharSequence rawPassword, String encodedPassword) {
        return !matches(rawPassword, encodedPassword);
    }

    public default boolean anyMatch(CharSequence rawPassword, String... encodedPasswords) {
        return Arrays.stream(encodedPasswords).anyMatch(encodedPassword -> matches(rawPassword, encodedPassword));
    }

    public default boolean noneMatch(CharSequence rawPassword, String... encodedPasswords) {
        return Arrays.stream(encodedPasswords).noneMatch(encodedPassword -> matches(rawPassword, encodedPassword));
    }

    @Override
    public default boolean upgradeEncoding(String encodedPassword) {
        return true;
    }

}
