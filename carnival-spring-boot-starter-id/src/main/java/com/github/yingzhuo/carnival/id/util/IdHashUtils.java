/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.id.util;

import com.github.yingzhuo.carnival.id.IdHash;
import com.github.yingzhuo.carnival.spring.SpringUtils;

/**
 * @author 应卓
 * @since 1.7.2
 */
public final class IdHashUtils {

    private IdHashUtils() {
    }

    public static String encode(long... ids) {
        return SpringUtils.getBean(IdHash.class).encode(ids);
    }

    public static long[] ids(String hash) {
        return SpringUtils.getBean(IdHash.class).decode(hash);
    }

}
