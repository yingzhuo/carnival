/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.security.token.resolver;

import com.github.yingzhuo.carnival.security.token.StringToken;
import com.github.yingzhuo.carnival.security.token.Token;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Optional;

/**
 * @author 应卓
 * @since 1.10.2
 */
public class HeaderTokenResolver implements TokenResolver {

    protected static final String EMPTY_PREFIX = "";

    private final String headerName;
    private final String prefix;
    private final int prefixLen;

    public HeaderTokenResolver(String headerName) {
        this(headerName, EMPTY_PREFIX);
    }

    public HeaderTokenResolver(String headerName, String prefix) {
        if (prefix == null) prefix = EMPTY_PREFIX;
        this.headerName = headerName;
        this.prefix = prefix;
        this.prefixLen = prefix.length();
    }

    @Override
    public Optional<Token> resolve(NativeWebRequest request) {

        String headerValue = request.getHeader(headerName);

        if (headerValue == null || !headerValue.startsWith(prefix)) {
            return Optional.empty();
        }

        headerValue = headerValue.substring(prefixLen);

        if (headerValue.split("\\.").length == 2 && !headerValue.endsWith(".")) {
            headerValue += ".";
        }

        return Optional.of(new StringToken(headerValue));
    }

}
