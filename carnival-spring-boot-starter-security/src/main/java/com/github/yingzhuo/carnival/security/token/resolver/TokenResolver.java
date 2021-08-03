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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author 应卓
 * @since 1.10.2
 */
@FunctionalInterface
public interface TokenResolver {

    public static Builder builder() {
        return new Builder();
    }

    public Optional<Token> resolve(NativeWebRequest request);

    public static class Builder {
        private final List<TokenResolver> list = new ArrayList<>();

        private Builder() {
        }

        public Builder add(TokenResolver... resolvers) {
            if (resolvers != null && resolvers.length != 0) {
                list.addAll(Arrays.asList(resolvers));
            }
            return this;
        }

        public Builder fromHttpHeader(String headName) {
            list.add(new HeaderTokenResolver(headName));
            return this;
        }

        public Builder fromHttpHeader(String headName, String prefix) {
            list.add(new HeaderTokenResolver(headName, prefix));
            return this;
        }

        public Builder fromQuery(String paramName) {
            list.add(new QueryTokenResolver(paramName));
            return this;
        }

        public Builder fromQuery(String paramName, String prefix) {
            list.add(new QueryTokenResolver(paramName, prefix));
            return this;
        }

        public Builder bearerToken() {
            list.add(BearerTokenResolver.INSTANCE);
            return this;
        }

        public Builder basicToken() {
            list.add(BasicTokenResolver.INSTANCE);
            return this;
        }

        public TokenResolver build() {
            if (!list.isEmpty()) {
                return request -> Optional.empty();
            }
            return new ChainedTokenResolver(list);
        }
    }

}
