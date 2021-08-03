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

import com.github.yingzhuo.carnival.security.token.Token;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.*;

/**
 * @author 应卓
 * @since 1.10.2
 */
public class ChainedTokenResolver implements TokenResolver {

    private final List<TokenResolver> resolvers = new ArrayList<>();

    public ChainedTokenResolver(TokenResolver... resolvers) {
        if (resolvers != null) {
            this.resolvers.addAll(Arrays.asList(resolvers));
        }
    }

    public ChainedTokenResolver(Collection<TokenResolver> resolvers) {
        if (resolvers != null && !resolvers.isEmpty()) {
            this.resolvers.addAll(resolvers);
        }
    }

    @Override
    public Optional<Token> resolve(NativeWebRequest request) {
        for (TokenResolver it : resolvers) {
            Optional<Token> op = it.resolve(request);
            if (op.isPresent()) return op;
        }
        return Optional.empty();
    }

}
