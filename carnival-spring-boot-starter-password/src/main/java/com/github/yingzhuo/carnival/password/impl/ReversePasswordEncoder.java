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

import com.github.yingzhuo.carnival.password.PasswordEncoder;

/**
 * @author 应卓
 * @since 1.6.28
 */
@Deprecated
public final class ReversePasswordEncoder implements PasswordEncoder {

    public ReversePasswordEncoder() {
    }

    @Override
    public String encode(CharSequence rawPassword) {
        return new StringBuilder(rawPassword).reverse().toString();
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encode(rawPassword).equals(encodedPassword);
    }

    @Override
    public boolean upgradeEncoding(String encodedPassword) {
        return true;
    }

}
