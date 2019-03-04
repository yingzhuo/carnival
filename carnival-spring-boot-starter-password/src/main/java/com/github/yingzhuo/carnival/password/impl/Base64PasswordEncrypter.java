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

import com.github.yingzhuo.carnival.password.PasswordEncrypter;
import org.apache.commons.codec.binary.Base64;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * @author 应卓
 */
public class Base64PasswordEncrypter implements PasswordEncrypter {

    @Override
    public String encrypt(String password) {
        Objects.requireNonNull(password);
        return Base64.encodeBase64URLSafeString(password.getBytes(StandardCharsets.UTF_8));
    }

}
