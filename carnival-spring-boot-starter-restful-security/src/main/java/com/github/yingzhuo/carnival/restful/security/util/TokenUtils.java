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

import com.github.yingzhuo.carnival.restful.security.core.RestfulSecurityContext;
import com.github.yingzhuo.carnival.restful.security.token.Token;

/**
 * @author 应卓
 */
@SuppressWarnings("unchecked")
public final class TokenUtils {

    /**
     * 获取令牌
     *
     * @param <T> 令牌类型
     * @return 令牌
     */
    public static <T extends Token> T get() {
        return (T) RestfulSecurityContext.getToken().orElse(null);
    }

    // -----------------------------------------------------------------------------------------------------------------

    private TokenUtils() {
    }

}
