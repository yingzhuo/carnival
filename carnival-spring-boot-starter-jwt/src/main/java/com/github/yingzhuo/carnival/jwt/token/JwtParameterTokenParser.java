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

import com.github.yingzhuo.carnival.restful.security.parser.HttpParameterTokenParser;
import com.github.yingzhuo.carnival.restful.security.token.StringToken;
import com.github.yingzhuo.carnival.restful.security.token.Token;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Locale;
import java.util.Optional;

/**
 * @author 应卓
 * @see com.github.yingzhuo.carnival.common.parser.Parser
 * @see com.github.yingzhuo.carnival.restful.security.parser.HttpHeaderTokenParser
 * @see com.github.yingzhuo.carnival.restful.security.parser.HttpParameterTokenParser
 * @since 1.0.3
 */
public class JwtParameterTokenParser extends HttpParameterTokenParser {

    public JwtParameterTokenParser(String parameterName) {
        super(parameterName);
    }

    @Override
    public Optional<Token> parse(NativeWebRequest request, Locale locale) {
        return super.parse(request, locale).map(st -> new StringToken(st.toString()));
    }

}
