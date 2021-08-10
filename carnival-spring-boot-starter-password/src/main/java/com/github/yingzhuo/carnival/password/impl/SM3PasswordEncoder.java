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

import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.crypto.digest.Digester;
import com.github.yingzhuo.carnival.password.PasswordEncoder;

/**
 * @author 应卓
 * @since 1.10.5
 */
public class SM3PasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence rawPassword) {
        Digester digester = DigestUtil.digester("sm3");
        return digester.digestHex(rawPassword.toString());
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encodedPassword.equals(encode(rawPassword));
    }

}
