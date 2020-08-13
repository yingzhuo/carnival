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

import com.github.yingzhuo.carnival.password.StringEncryptor;
import org.springframework.security.crypto.encrypt.Encryptors;

/**
 * @author 应卓
 * @since 1.7.1
 */
public class SimpleStringEncryptor implements StringEncryptor {

    @Override
    public String encrypt(String text, String password, String salt) {
        return Encryptors.text(password, salt).encrypt(text);
    }

    @Override
    public String decrypt(String encryptedText, String password, String salt) {
        return Encryptors.text(password, salt).decrypt(encryptedText);
    }

}
