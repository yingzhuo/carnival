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

import java.util.UUID;

/**
 * @author 应卓
 * @since 1.1.3
 */
public final class UUIDUtils {

    private UUIDUtils() {
    }

    public static String uuid32() {
        return uuid36().replaceAll("-", "");
    }

    public static String uuid36() {
        return UUID.randomUUID().toString();
    }

}
