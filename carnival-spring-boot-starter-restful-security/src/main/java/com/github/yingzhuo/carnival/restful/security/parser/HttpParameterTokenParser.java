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

import java.util.Objects;
import java.util.Optional;

/**
 * @author 应卓
 */
public class HttpParameterTokenParser implements TokenParser {

    private final String parameterName;
    private int order = 0;

    public HttpParameterTokenParser(String parameterName) {
        this.parameterName = Objects.requireNonNull(parameterName);
    }

    @Override
    public Optional<Token> parse(NativeWebRequest request) {
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

    @Override
    public int getOrder() {
        return this.order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

}
