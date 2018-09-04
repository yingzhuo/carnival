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

/**
 * @author 应卓
 */
public final class UUIDUtils {

    private UUIDUtils() {
        super();
    }

    public static String randomUUID32() {
        return randomUUID36().replaceAll("-", "");
    }

    public static String randomUUID36() {
        return java.util.UUID.randomUUID().toString();
    }

}
