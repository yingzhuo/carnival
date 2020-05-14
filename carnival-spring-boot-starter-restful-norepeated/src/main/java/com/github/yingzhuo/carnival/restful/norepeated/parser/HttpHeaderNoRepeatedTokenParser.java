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

import org.springframework.util.StringUtils;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Objects;
import java.util.Optional;

/**
 * @author 应卓
 * @since 1.6.7
 */
public class HttpHeaderNoRepeatedTokenParser implements NoRepeatedTokenParser {

    private final String headerName;
    private final String prefix;
    private final int prefixLen;

    public HttpHeaderNoRepeatedTokenParser(String headerName) {
        this(headerName, "");
    }

    public HttpHeaderNoRepeatedTokenParser(String headerName, String prefix) {
        this.headerName = Objects.requireNonNull(headerName);
        this.prefix = prefix != null ? prefix : "";
        this.prefixLen = this.prefix.length();
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
