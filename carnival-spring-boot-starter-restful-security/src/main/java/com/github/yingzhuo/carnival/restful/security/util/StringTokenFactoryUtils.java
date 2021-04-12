/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.security.util;

import com.github.yingzhuo.carnival.restful.security.factory.StringTokenFactory;
import com.github.yingzhuo.carnival.spring.SpringUtils;

/**
 * @author 应卓
 * @since 1.8.2
 */
public final class StringTokenFactoryUtils {

    private StringTokenFactoryUtils() {
    }

    @SuppressWarnings("unchecked")
    public static <T> String create(T user) {
        return SpringUtils.getBean(StringTokenFactory.class).create(user);
    }

}
