/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.password.algorithm;

import com.github.yingzhuo.carnival.password.StringEncryptor;
import org.springframework.security.crypto.encrypt.Encryptors;

/**
 * @author 应卓
 * @since 1.7.1
 */
public enum StringEncryptorAlgorithm implements StringEncryptor {

    simple {
        @Override
        public String encrypt(String text, String password, String salt) {
            return Encryptors.text(password, salt).encrypt(text);
        }

        @Override
        public String decrypt(String encryptedText, String password, String salt) {
            return Encryptors.text(password, salt).decrypt(encryptedText);
        }
    },

    delux {
        @Override
        public String encrypt(String text, String password, String salt) {
            return Encryptors.delux(password, salt).encrypt(text);
        }

        @Override
        public String decrypt(String encryptedText, String password, String salt) {
            return Encryptors.delux(password, salt).decrypt(encryptedText);
        }
    },

    @Deprecated
    noop {
        @Override
        public String encrypt(String text, String password, String salt) {
            return Encryptors.noOpText().encrypt(text);
        }

        @Override
        public String decrypt(String encryptedText, String password, String salt) {
            return Encryptors.noOpText().decrypt(encryptedText);
        }
    };

}
