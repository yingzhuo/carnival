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

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author 应卓
 * @since 1.6.28
 */
@Deprecated
public class Base64PasswordEncoder implements PasswordEncoder {

    private final Charset charset;

    public Base64PasswordEncoder() {
        this(StandardCharsets.UTF_8);
    }

    public Base64PasswordEncoder(Charset charset) {
        this.charset = charset;
    }

    @Override
    public String encode(CharSequence rawPassword) {
        return Base64.getUrlEncoder().encodeToString(rawPassword.toString().getBytes(charset));
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return new String(Base64.getUrlDecoder().decode(encodedPassword.getBytes(charset))).equals(rawPassword.toString());
    }

    @Override
    public boolean upgradeEncoding(String encodedPassword) {
        return true;
    }

}
