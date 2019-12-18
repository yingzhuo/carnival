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
 * @since 1.3.6
 * @author 应卓
 */
public class HttpHeaderStepTokenParser implements StepTokenParser {

    private final String headerName;
    private final String prefix;

    public HttpHeaderStepTokenParser(String headerName) {
        this(headerName, "");
    }

    public HttpHeaderStepTokenParser(String headerName, String prefix) {
        this.headerName = headerName;
        this.prefix = prefix;
    }

    @Override
    public Optional<String> parser(NativeWebRequest request) {
        String value = request.getHeader(this.headerName);

        if (value == null) {
            return Optional.empty();
        } else {
            return Optional.of(value.substring(prefix.length()));
        }
    }

}
