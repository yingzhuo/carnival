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

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author 应卓
 * @since 1.6.6
 */
public class Base64PasswordEncrypter extends AbstractPasswordEncrypter {

    @Override
    public String doEncrypt(String rawPassword, String leftSalt, String rightSalt) {
        return Base64.getUrlEncoder()
                .encodeToString(
                        contact(rawPassword, leftSalt, rightSalt).getBytes(StandardCharsets.UTF_8)
                );
    }

}
