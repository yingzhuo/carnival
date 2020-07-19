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

/**
 * @author 应卓
 * @since 1.6.27
 */
public interface PasswordEncoder extends org.springframework.security.crypto.password.PasswordEncoder {

    public default boolean notMatches(CharSequence rawPassword, String encodedPassword) {
        return !matches(rawPassword, encodedPassword);
    }

}
