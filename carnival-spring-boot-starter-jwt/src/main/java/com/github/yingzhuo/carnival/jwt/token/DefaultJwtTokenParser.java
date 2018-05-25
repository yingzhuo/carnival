/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.jwt.token;

import com.github.yingzhuo.carnival.restful.security.TokenParser;
import com.github.yingzhuo.carnival.restful.security.impl.HttpHeaderTokenParser;

/**
 * @author 应卓
 */
public class DefaultJwtTokenParser extends HttpHeaderTokenParser implements TokenParser {

    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer ";

    public DefaultJwtTokenParser() {
        super(AUTHORIZATION, BEARER);
    }

}
