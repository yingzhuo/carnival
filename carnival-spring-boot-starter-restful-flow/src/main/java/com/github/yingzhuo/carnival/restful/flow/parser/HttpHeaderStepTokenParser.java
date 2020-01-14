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

import org.springframework.util.StringUtils;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Optional;

/**
 * @author 应卓
 * @since 1.3.6
 */
public class HttpHeaderStepTokenParser implements StepTokenParser {

    private final String headerName;
    private final String prefix;
    private final int prefixLen;

    public HttpHeaderStepTokenParser(String headerName) {
        this(headerName, "");
    }

    public HttpHeaderStepTokenParser(String headerName, String prefix) {
        this.headerName = headerName;
        this.prefix = prefix;
        this.prefixLen = prefix.length();
    }

    @Override
    public Optional<String> parse(NativeWebRequest request) {
        String headerValue = request.getHeader(headerName);

        if (!StringUtils.hasText(headerValue)) {
            return Optional.empty();
        }

        if (headerValue.length() <= prefixLen) {
            return Optional.empty();
        }

        if (!headerValue.startsWith(prefix)) {
            return Optional.empty();
        }

        String tokenValue = headerValue.substring(prefixLen);

        if (StringUtils.isEmpty(tokenValue)) {
            return Optional.empty();
        }

        return Optional.of(tokenValue);
    }

}
