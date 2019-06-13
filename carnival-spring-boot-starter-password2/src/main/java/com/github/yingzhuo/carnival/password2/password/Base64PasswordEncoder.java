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

import lombok.val;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Objects;

/**
 * @author 应卓
 */
@Deprecated
public class Base64PasswordEncoder implements PasswordEncoder {

    public static final PasswordEncoder INSTANCE = new Base64PasswordEncoder();

    private Base64PasswordEncoder() {
    }

    @Override
    public String encode(CharSequence rawPassword) {
        Objects.requireNonNull(rawPassword);
        return Base64.getEncoder().encodeToString(rawPassword.toString().getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        Objects.requireNonNull(rawPassword);
        Objects.requireNonNull(encodedPassword);

        val s = Base64.getEncoder().encodeToString(rawPassword.toString().getBytes(StandardCharsets.UTF_8));
        return encodedPassword.equals(s);
    }

    @Override
    public boolean upgradeEncoding(String encodedPassword) {
        return true;
    }

}
