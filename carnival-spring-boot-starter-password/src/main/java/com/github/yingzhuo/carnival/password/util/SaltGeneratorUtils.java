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

import com.github.yingzhuo.carnival.password.encryptor.SaltGenerator;
import com.github.yingzhuo.carnival.spring.SpringUtils;

/**
 * @author 应卓
 * @since 1.7.1
 */
public final class SaltGeneratorUtils {

    private SaltGeneratorUtils() {
    }

    public static String generate() {
        return SpringUtils.getBean(SaltGenerator.class).generate();
    }

}
