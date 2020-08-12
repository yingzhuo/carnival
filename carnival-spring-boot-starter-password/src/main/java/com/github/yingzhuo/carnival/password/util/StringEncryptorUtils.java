/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.password.util;

import com.github.yingzhuo.carnival.password.StringEncryptor;
import com.github.yingzhuo.carnival.spring.SpringUtils;

/**
 * @author 应卓
 * @since 1.7.1
 */
public final class StringEncryptorUtils {

    private StringEncryptorUtils() {
    }

    public static String encrypt(String text, String password, String salt) {
        return SpringUtils.getBean(StringEncryptor.class)
                .encrypt(text, password, salt);
    }

    public static String decrypt(String encryptedText, String password, String salt) {
        return SpringUtils.getBean(StringEncryptor.class)
                .decrypt(encryptedText, password, salt);
    }

}
