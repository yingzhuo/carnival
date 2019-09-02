/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.security.jwt.parser;

import com.github.yingzhuo.carnival.restful.security.jwt.token.JwtToken;
import com.github.yingzhuo.carnival.restful.security.parser.HttpHeaderTokenParser;
import com.github.yingzhuo.carnival.restful.security.token.StringToken;
import com.github.yingzhuo.carnival.restful.security.token.Token;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Optional;

/**
 * @author 应卓
 * @since 1.1.4
 */
public class JwtTokenParser extends HttpHeaderTokenParser {

    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer ";

    public JwtTokenParser() {
        super(AUTHORIZATION, BEARER);
    }

    @Override
    public Optional<Token> parse(NativeWebRequest request) {
        return super.parse(request).map(t -> JwtToken.of(((StringToken) t).getValue()));
    }

}
