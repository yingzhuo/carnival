/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.norepeated.parser;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Objects;
import java.util.Optional;

/**
 * @author 应卓
 * @since 1.6.7
 */
public class HttpParameterNoRepeatedTokenParser implements NoRepeatedTokenParser {

    private final String parameterName;

    public HttpParameterNoRepeatedTokenParser(String parameterName) {
        this.parameterName = Objects.requireNonNull(parameterName);
    }

    @Override
    public Optional<String> parse(NativeWebRequest request) {
        final String tokenValue = request.getParameter(parameterName);

        if (StringUtils.isBlank(tokenValue)) {
            return Optional.empty();
        }

        return Optional.of(tokenValue);
    }

}
