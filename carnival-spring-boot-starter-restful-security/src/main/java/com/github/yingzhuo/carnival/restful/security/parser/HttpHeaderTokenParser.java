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

import java.util.Optional;

/**
 * @author 应卓
 */
public class HttpHeaderTokenParser implements TokenParser {

    private final String headerName;
    private final String prefix;
    private final int prefixLen;

    public HttpHeaderTokenParser(String headerName) {
        this(headerName, "");
    }

    public HttpHeaderTokenParser(String headerName, String prefix) {
        this.headerName = headerName;
        this.prefix = prefix;
        this.prefixLen = prefix.length();
    }

    @Override
    public Optional<Token> parse(NativeWebRequest request) {
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

        String tokenValue = headerValue.substring(prefixLen, headerValue.length());
        return Optional.of(StringToken.of(tokenValue));
    }

    public String getHeaderName() {
        return headerName;
    }

    public String getPrefix() {
        return prefix;
    }

}
