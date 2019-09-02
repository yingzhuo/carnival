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

import com.github.yingzhuo.carnival.restful.security.token.Token;
import org.springframework.core.Ordered;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Optional;

/**
 * @author 应卓
 * @see com.github.yingzhuo.carnival.restful.security.parser.TokenParser
 * @see com.github.yingzhuo.carnival.restful.security.realm.DebuggingUserDetailsRealm
 * @since 1.1.6
 */
public class DebuggingTokenParser implements TokenParser {

    @Override
    public Optional<Token> parse(NativeWebRequest webRequest) {
        return Optional.of(new EmptyToken());
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }

    // -----------------------------------------------------------------------------------------------------------------

    private static class EmptyToken implements Token {
        @Override
        public String toString() {
            return "EmptyToken{}";
        }
    }

}
