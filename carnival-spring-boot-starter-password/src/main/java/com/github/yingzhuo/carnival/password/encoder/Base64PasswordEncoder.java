/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.password.encoder;

import cn.hutool.core.codec.Base64;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author 应卓
 * @since 1.6.28
 */
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
        return Base64.encode(rawPassword, charset);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return Base64.decodeStr(encodedPassword, charset).equals(rawPassword.toString());
    }

}
