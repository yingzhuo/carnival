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

import com.github.yingzhuo.carnival.security.token.MutableToken;
import com.github.yingzhuo.carnival.security.token.Token;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Optional;

/**
 * @author 应卓
 * @since 1.10.9
 */
public final class FixedTokenResolver implements TokenResolver {

    private final Token token;

    private FixedTokenResolver(final String key) {
        this.token = MutableToken.builder()
                .key(key)
                .authenticated(false)
                .details(null)
                .userDetails(null)
                .creationTime(System.currentTimeMillis())
                .build();
    }

    public static FixedTokenResolver of(String key) {
        return new FixedTokenResolver(key);
    }

    @Override
    public Optional<Token> resolve(NativeWebRequest request) {
        return Optional.ofNullable(token);
    }

}
