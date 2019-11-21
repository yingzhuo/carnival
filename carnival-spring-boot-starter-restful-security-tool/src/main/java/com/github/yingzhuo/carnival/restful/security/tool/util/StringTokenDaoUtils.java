/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.security.tool.util;

import com.github.yingzhuo.carnival.restful.security.tool.tokendao.StringTokenDao;
import com.github.yingzhuo.carnival.spring.SpringUtils;

/**
 * @author 应卓
 * @since 1.2.5
 */
public final class StringTokenDaoUtils {

    public static void save(String key, String token) {
        SpringUtils.getBean(StringTokenDao.class).save(key, token);
    }

    public static String find(String key) {
        return SpringUtils.getBean(StringTokenDao.class).find(key);
    }

    public static boolean matches(String key, String token) {
        return SpringUtils.getBean(StringTokenDao.class).matches(key, token);
    }

    // ------------------------------------------------------------------

    private StringTokenDaoUtils() {
    }

}
