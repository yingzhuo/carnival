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

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author 应卓
 * @since 1.6.27
 */
@Deprecated
public final class NoOpIgnoreCasePasswordEncoder implements PasswordEncoder {

    private static final PasswordEncoder INSTANCE = new NoOpIgnoreCasePasswordEncoder();

    public static PasswordEncoder getInstance() {
        return INSTANCE;
    }

    private NoOpIgnoreCasePasswordEncoder() {
    }

    public String encode(CharSequence rawPassword) {
        return rawPassword.toString();
    }

    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return rawPassword.toString().equalsIgnoreCase(encodedPassword);
    }

}
