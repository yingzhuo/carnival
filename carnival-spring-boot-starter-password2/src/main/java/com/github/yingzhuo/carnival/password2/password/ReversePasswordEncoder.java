/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.password2.password;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author 应卓
 */
@Deprecated
public class ReversePasswordEncoder implements PasswordEncoder {

    private static final PasswordEncoder INSTANCE = new ReversePasswordEncoder();

    public static PasswordEncoder getInstance() {
        return INSTANCE;
    }

    @Override
    public String encode(CharSequence rawPassword) {
        return new StringBuilder(rawPassword).reverse().toString();
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return StringUtils.equals(encode(rawPassword), encodedPassword);
    }

    @Override
    public boolean upgradeEncoding(String encodedPassword) {
        return true;
    }

}
