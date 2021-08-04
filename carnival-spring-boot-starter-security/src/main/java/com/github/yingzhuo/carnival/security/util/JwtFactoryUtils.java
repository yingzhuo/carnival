/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.security.util;

import com.github.yingzhuo.carnival.security.jwt.factory.JwtTokenFactory;
import com.github.yingzhuo.carnival.security.jwt.factory.JwtTokenMetadata;
import com.github.yingzhuo.carnival.spring.SpringUtils;

/**
 * @author 应卓
 */
public final class JwtFactoryUtils {

    private JwtFactoryUtils() {
    }

    public static String create(JwtTokenMetadata metadata) {
        return SpringUtils.getBean(JwtTokenFactory.class).create(metadata);
    }

    public static String create(JwtTokenMetadata.Builder builder) {
        return create(builder.build());
    }

}
