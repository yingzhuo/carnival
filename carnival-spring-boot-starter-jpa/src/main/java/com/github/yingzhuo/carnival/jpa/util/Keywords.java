/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.jpa.util;

/**
 * @author 应卓
 * @since 1.10.22
 */
public final class Keywords {

    private Keywords() {
    }

    public static String normalize(String keyword) {
        if (keyword == null) {
            return null;
        }
        return normalizeLeft(normalizeRight(keyword));
    }

    public static String normalizeLeft(String keyword) {
        if (keyword == null) {
            return null;
        }
        if (!keyword.startsWith("%")) {
            keyword = "%" + keyword;
        }
        return keyword;
    }

    public static String normalizeRight(String keyword) {
        if (keyword == null) {
            return null;
        }
        if (!keyword.endsWith("%")) {
            keyword = keyword + "%";
        }
        return keyword;
    }

}
