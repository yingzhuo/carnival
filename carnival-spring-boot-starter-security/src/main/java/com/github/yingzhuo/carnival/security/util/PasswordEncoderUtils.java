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

import com.github.yingzhuo.carnival.spring.SpringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author 应卓
 * @since 1.10.28
 */
public final class PasswordEncoderUtils {

    private PasswordEncoderUtils() {
    }

    public static String encode(CharSequence rawPassword) {
        return SpringUtils.getBean(PasswordEncoder.class).encode(rawPassword);
    }

    public static boolean matches(CharSequence rawPassword, String encodedPassword) {
        return SpringUtils.getBean(PasswordEncoder.class).matches(rawPassword, encodedPassword);
    }

    public static boolean notMatches(CharSequence rawPassword, String encodedPassword) {
        return !matches(rawPassword, encodedPassword);
    }

}
