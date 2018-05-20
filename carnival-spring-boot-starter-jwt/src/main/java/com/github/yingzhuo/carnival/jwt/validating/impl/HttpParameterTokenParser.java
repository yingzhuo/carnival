/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.jwt.validating.impl;

import com.github.yingzhuo.carnival.jwt.validating.TokenParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.WebRequest;

import java.util.Optional;

/**
 * @author 应卓
 */
@Slf4j
public class HttpParameterTokenParser implements TokenParser {

    private final String parameterName;

    public HttpParameterTokenParser(String parameterName) {
        this.parameterName = parameterName;
    }

    @Override
    public Optional<String> parse(WebRequest request) {
        String token = request.getParameter(parameterName);
        log.debug("token: {}", token);
        return Optional.ofNullable(token);
    }

    public String getParameterName() {
        return parameterName;
    }

}
