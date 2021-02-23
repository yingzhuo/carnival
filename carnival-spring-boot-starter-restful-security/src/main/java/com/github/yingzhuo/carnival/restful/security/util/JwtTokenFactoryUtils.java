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

import com.github.yingzhuo.carnival.restful.security.factory.JwtTokenFactory;
import com.github.yingzhuo.carnival.restful.security.factory.JwtTokenInfo;
import com.github.yingzhuo.carnival.spring.SpringUtils;

/**
 * @author 应卓
 * @since 1.1.6
 */
public final class JwtTokenFactoryUtils {

    private JwtTokenFactoryUtils() {
    }

    public static String create(JwtTokenInfo info) {
        return SpringUtils.getBean(JwtTokenFactory.class).create(info);
    }

    public static String create(JwtTokenInfo.Builder builder) {
        return SpringUtils.getBean(JwtTokenFactory.class).create(builder);
    }

}
