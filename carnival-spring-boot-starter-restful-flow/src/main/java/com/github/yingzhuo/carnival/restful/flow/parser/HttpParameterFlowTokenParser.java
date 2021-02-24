/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.flow.parser;

import org.springframework.web.context.request.NativeWebRequest;

import java.util.Optional;

/**
 * @author 应卓
 * @since 1.3.6
 */
public class HttpParameterFlowTokenParser implements FlowTokenParser {

    private static final String DEFAULT_PARAMETER_NAME = "_step_token";

    private final String parameterName;

    public HttpParameterFlowTokenParser() {
        this(DEFAULT_PARAMETER_NAME);
    }

    public HttpParameterFlowTokenParser(String parameterName) {
        this.parameterName = parameterName;
    }

    @Override
    public Optional<String> parse(NativeWebRequest request) {
        return Optional.ofNullable(request.getParameter(parameterName));
    }

}
