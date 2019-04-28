/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.security.jwt;

import com.github.yingzhuo.carnival.common.parser.Parser;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.Optional;

/**
 * @author 应卓
 */
public class JwtTokenParser implements Parser<HttpServletRequest, JwtToken> {

    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer ";
    private static final int BEARER_LEN = BEARER.length();

    @Override
    public Optional<JwtToken> parse(HttpServletRequest request, Locale locale) {

        String headerValue = request.getHeader(AUTHORIZATION);

        if (headerValue == null) {
            return Optional.empty();
        }

        if (!headerValue.startsWith(BEARER)) {
            return Optional.empty();
        }

        String rawToken = headerValue.substring(BEARER_LEN);
        return Optional.of(new JwtToken(rawToken));
    }

}
