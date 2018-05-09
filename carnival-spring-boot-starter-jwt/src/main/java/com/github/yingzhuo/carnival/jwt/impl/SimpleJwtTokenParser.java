/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.jwt.impl;

import com.github.yingzhuo.carnival.jwt.JwtTokenParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Optional;

@Slf4j
public class SimpleJwtTokenParser implements JwtTokenParser {

    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer ";
    private static final int BEARER_LEN = BEARER.length();

    @Override
    public Optional<String> parse(NativeWebRequest request) {
        String headerValue = request.getHeader(AUTHORIZATION);

        if (!StringUtils.hasText(headerValue)) {
            return Optional.empty();
        }

        if (headerValue.length() <= BEARER_LEN) {
            return Optional.empty();
        }

        if (!headerValue.startsWith(BEARER)) {
            return Optional.empty();
        }

        return Optional.of(headerValue.substring(BEARER_LEN, headerValue.length()));
    }

}
