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

import com.github.yingzhuo.carnival.password.encoder.PasswordEncoder;
import com.github.yingzhuo.carnival.spring.SpringUtils;

/**
 * @author 应卓
 * @since 1.6.27
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
        return SpringUtils.getBean(PasswordEncoder.class).notMatches(rawPassword, encodedPassword);
    }

}
