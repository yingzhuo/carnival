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
import com.github.yingzhuo.carnival.restful.security.token.Token;
import lombok.val;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Locale;
import java.util.Optional;

/**
 * @author 应卓
 * @see com.github.yingzhuo.carnival.common.parser.Parser
 * @see com.github.yingzhuo.carnival.restful.security.parser.HttpHeaderTokenParser
 * @see com.github.yingzhuo.carnival.restful.security.parser.HttpParameterTokenParser
 */
public class JwtTokenParser extends HttpHeaderTokenParser {

    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer ";

    public JwtTokenParser() {
        super(AUTHORIZATION, BEARER);
    }

    @Override
    public Optional<Token> parse(NativeWebRequest request, Locale locale) {
        val op = super.parse(request, locale);

        if (op.isPresent()) {
            val tokenValue = op.get().toString();
            val parts = tokenValue.split("\\.");

            if (parts.length != 3) {
                return Optional.empty();
            } else {
                return Optional.of(new JwtToken(tokenValue));
            }
        }

        return Optional.empty();
    }

}
