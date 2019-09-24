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

import com.github.yingzhuo.carnival.password.SaltPair;
import org.apache.commons.lang3.RandomStringUtils;

/**
 * @author 应卓
 * @since 1.1.12
 */
public final class SaltUtils {

    public static String gen(int length) {
        return RandomStringUtils.randomAlphabetic(length);
    }

    public static SaltPair genPair(int length) {
        return new SaltPair(
                RandomStringUtils.randomAlphabetic(length),
                RandomStringUtils.randomAlphabetic(length)
        );
    }

    // -----------------------------------------------------------------------------------------------------------------

    private SaltUtils() {
    }

}
