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

import com.github.yingzhuo.carnival.password.PasswordEncrypter;
import com.github.yingzhuo.carnival.spring.SpringUtils;

/**
 * @author 应卓
 */
public final class PasswordEncrypterUtils {

    private PasswordEncrypterUtils() {
    }

    public static String encrypt(String password) {
        return SpringUtils.getBean(PasswordEncrypter.class).encrypt(password);
    }

    public static boolean matches(String password, String encrypted) {
        return SpringUtils.getBean(PasswordEncrypter.class).matches(password, encrypted);
    }

    public static boolean notMatches(String password, String encrypted) {
        return SpringUtils.getBean(PasswordEncrypter.class).notMatches(password, encrypted);
    }

}
