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

    public static String gen(int len) {
        return RandomStringUtils.randomAlphabetic(len);
    }

    public static SaltPair genPair(int len) {
        return SaltPair.of(RandomStringUtils.randomAlphabetic(len), RandomStringUtils.randomAlphabetic(len));
    }

    // -----------------------------------------------------------------------------------------------------------------

    private SaltUtils() {
    }

}
