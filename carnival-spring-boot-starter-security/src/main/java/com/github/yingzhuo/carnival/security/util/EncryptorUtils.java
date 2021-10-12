/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.security.util;

import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.keygen.KeyGenerators;

/**
 * @author 应卓
 * @since 1.10.29
 */
public final class EncryptorUtils {

    private EncryptorUtils() {
    }

    public static String createSalt() {
        return KeyGenerators.string().generateKey();
    }

    public static String encrypt(String text, String salt, String password) {
        return Encryptors.text(password, salt).encrypt(text);
    }

    public static String decrypt(String encryptedText, String salt, String password) {
        return Encryptors.text(password, salt).decrypt(encryptedText);
    }

}
