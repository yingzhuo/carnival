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

    public static String encrypt(String rawPassword) {
        return passwordEncrypter().encrypt(rawPassword);
    }

    public static String encrypt(String rawPassword, String rightSalt) {
        return passwordEncrypter().encrypt(rawPassword, rightSalt);
    }

    public static String encrypt(String rawPassword, String leftSalt, String rightSalt) {
        return passwordEncrypter().encrypt(rawPassword, leftSalt, rightSalt);
    }

    public static boolean matches(String rawPassword, String encryptedPassword) {
        return passwordEncrypter().matches(rawPassword, encryptedPassword);
    }

    public static boolean matches(String rawPassword, String rightSalt, String encryptedPassword) {
        return passwordEncrypter().matches(rawPassword, rightSalt, encryptedPassword);
    }

    public static boolean matches(String rawPassword, String leftSalt, String rightSalt, String encryptedPassword) {
        return passwordEncrypter().matches(rawPassword, leftSalt, rightSalt, encryptedPassword);
    }

    public static boolean notMatches(String rawPassword, String encryptedPassword) {
        return passwordEncrypter().notMatches(rawPassword, encryptedPassword);
    }

    public static boolean notMatches(String rawPassword, String rightSalt, String encryptedPassword) {
        return passwordEncrypter().notMatches(rawPassword, rightSalt, encryptedPassword);
    }

    public static boolean notMatches(String rawPassword, String leftSalt, String rightSalt, String encryptedPassword) {
        return passwordEncrypter().notMatches(rawPassword, leftSalt, rightSalt, encryptedPassword);
    }

    private static PasswordEncrypter passwordEncrypter() {
        return SpringUtils.getBean(PasswordEncrypter.class);
    }

    // -----------------------------------------------------------------------------------------------------------------

    private PasswordEncrypterUtils() {
    }

}
