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

import com.github.yingzhuo.carnival.restful.security.parser.HttpHeaderTokenParser;
import com.github.yingzhuo.carnival.restful.security.parser.TokenParser;

/**
 * @author 应卓
 * @see com.github.yingzhuo.carnival.common.parser.Parser
 * @see com.github.yingzhuo.carnival.restful.security.parser.HttpHeaderTokenParser
 * @see com.github.yingzhuo.carnival.restful.security.parser.HttpParameterTokenParser
 */
public class JwtTokenParser extends HttpHeaderTokenParser implements TokenParser {

    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer ";

    public JwtTokenParser() {
        super(AUTHORIZATION, BEARER);
    }

}
