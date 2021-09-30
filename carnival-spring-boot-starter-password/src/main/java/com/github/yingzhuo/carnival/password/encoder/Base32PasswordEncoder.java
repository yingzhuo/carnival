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

import cn.hutool.core.codec.Base32;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author 应卓
 * @since 1.10.19
 */
public class Base32PasswordEncoder implements PasswordEncoder {

    private final Charset charset;

    public Base32PasswordEncoder() {
        this(StandardCharsets.UTF_8);
    }

    public Base32PasswordEncoder(Charset charset) {
        this.charset = charset;
    }

    @Override
    public String encode(CharSequence rawPassword) {
        return Base32.encode(rawPassword.toString(), charset);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return Base32.decodeStr(encodedPassword, charset).equals(rawPassword.toString());
    }

}
