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
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.WebRequest;

import java.util.Objects;
import java.util.Optional;

/**
 * @author 应卓
 */
@Slf4j
public class HttpHeaderTokenParser implements TokenParser {

    private final String headerName;
    private final String prefix;
    private final int prefixLen;

    public HttpHeaderTokenParser(String headerName) {
        this(headerName, "");
    }

    public HttpHeaderTokenParser(String headerName, String prefix) {
        this.headerName = Objects.requireNonNull(headerName);
        this.prefix = Objects.requireNonNull(prefix);
        this.prefixLen = this.prefix.length();
    }

    @Override
    public Optional<String> parse(WebRequest request) {
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

        String token = headerValue.substring(prefixLen, headerValue.length());

        log.debug("token: {}", token);

        return Optional.of(token);
    }

    public String getHeaderName() {
        return headerName;
    }

    public String getPrefix() {
        return prefix;
    }

    public int getPrefixLen() {
        return prefixLen;
    }

}
