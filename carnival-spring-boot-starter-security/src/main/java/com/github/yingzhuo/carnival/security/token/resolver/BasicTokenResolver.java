/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.security.token.resolver;

import org.springframework.http.HttpHeaders;

/**
 * @author 应卓
 * @since 1.10.3
 */
public final class BasicTokenResolver extends HeaderTokenResolver {

    public static final BasicTokenResolver INSTANCE = new BasicTokenResolver();

    private BasicTokenResolver() {
        super(HttpHeaders.AUTHORIZATION, "Basic ");
    }

}
