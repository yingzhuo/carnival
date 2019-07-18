/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.security.parser;

import com.github.yingzhuo.carnival.restful.security.token.StringToken;
import com.github.yingzhuo.carnival.restful.security.token.Token;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Locale;
import java.util.Optional;

/**
 * @author 应卓
 */
public class HttpParameterTokenParser implements TokenParser {

    private final String parameterName;

    public HttpParameterTokenParser(String parameterName) {
        this.parameterName = parameterName;
    }

    @Override
    public Optional<Token> parse(NativeWebRequest request, Locale locale) {
        final String tokenValue = request.getParameter(parameterName);

        if (StringUtils.isEmpty(tokenValue)) {
            return Optional.empty();
        } else {
            return Optional.of(StringToken.of(tokenValue));
        }
    }

    public String getParameterName() {
        return parameterName;
    }

}
