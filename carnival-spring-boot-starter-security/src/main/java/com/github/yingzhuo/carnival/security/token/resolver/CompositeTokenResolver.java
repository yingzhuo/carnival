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
import org.springframework.core.OrderComparator;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.*;

/**
 * @author 应卓
 * @since 1.10.3
 */
public final class CompositeTokenResolver implements TokenResolver {

    private final List<TokenResolver> resolvers = new ArrayList<>();

    public CompositeTokenResolver(TokenResolver... resolvers) {
        if (resolvers != null) {
            this.resolvers.addAll(Arrays.asList(resolvers));
        }
        init();
    }

    public CompositeTokenResolver(Collection<TokenResolver> resolvers) {
        if (resolvers != null && !resolvers.isEmpty()) {
            this.resolvers.addAll(resolvers);
        }
        init();
    }

    public static CompositeTokenResolver of(TokenResolver... resolvers) {
        return new CompositeTokenResolver(resolvers);
    }

    private void init() {
        OrderComparator.sort(resolvers);
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
