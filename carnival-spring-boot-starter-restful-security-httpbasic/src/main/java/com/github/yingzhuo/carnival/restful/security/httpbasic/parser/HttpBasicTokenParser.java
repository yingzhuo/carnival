/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.security.httpbasic.parser;

import com.github.yingzhuo.carnival.restful.security.parser.TokenParser;
import com.github.yingzhuo.carnival.restful.security.token.Token;
import com.github.yingzhuo.carnival.restful.security.token.UsernamePasswordToken;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.NativeWebRequest;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;

/**
 * @author 应卓
 * @see Token
 * @see TokenParser
 */
public class HttpBasicTokenParser implements TokenParser {

    private static final String BASIC = "Basic ";

    @Override
    public Optional<Token> parse(NativeWebRequest webRequest) {
        final String header = webRequest.getHeader(HttpHeaders.AUTHORIZATION);

        if (header == null) {
            return Optional.empty();
        }

        if (!header.startsWith(BASIC)) {
            return Optional.empty();
        }

        try {
            String usernameAndPassword = header.substring(BASIC.length());
            usernameAndPassword = new String(Base64.getUrlDecoder().decode(usernameAndPassword.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);

            final String[] up = usernameAndPassword.split(":", 2);

            if (up.length != 2) {
                return Optional.empty();
            } else {
                return Optional.of(new UsernamePasswordToken(up[0], up[1]));
            }

        } catch (Exception e) {
            return Optional.empty();
        }
    }

}
