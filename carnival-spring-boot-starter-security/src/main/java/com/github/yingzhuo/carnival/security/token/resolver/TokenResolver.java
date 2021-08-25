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

import com.github.yingzhuo.carnival.common.io.ResourceText;
import com.github.yingzhuo.carnival.security.token.Token;
import org.springframework.core.Ordered;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author 应卓
 * @since 1.10.2
 */
@FunctionalInterface
public interface TokenResolver extends Ordered, AuthenticationConverter {

    public static Builder builder() {
        return new Builder();
    }

    public Optional<Token> resolve(NativeWebRequest request);

    @Override
    public default int getOrder() {
        return 0;
    }

    @Override
    public default Authentication convert(HttpServletRequest request) {
        return resolve(new ServletWebRequest(request)).orElse(null);
    }

    // ----------------------------------------------------------------------------------------------------------------

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

        public Builder add(Collection<TokenResolver> resolvers) {
            if (resolvers != null && !resolvers.isEmpty()) {
                list.addAll(resolvers);
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

        // since 1.10.9
        public Builder fixed(String token) {
            list.add(FixedTokenResolver.of(token));
            return this;
        }

        // since 1.10.12
        public Builder fixedFromResource(String location) {
            return fixed(ResourceText.of(location).getText());
        }

        public TokenResolver build() {
            if (list.isEmpty()) {
                return EmptyTokenResolver.INSTANCE;
            }
            if (list.size() == 1) {
                return list.get(0);
            }
            return new CompositeTokenResolver(list);
        }
    }

}
