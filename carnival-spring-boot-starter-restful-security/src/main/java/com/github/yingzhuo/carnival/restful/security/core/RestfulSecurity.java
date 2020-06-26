/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.security.core;

import com.github.yingzhuo.carnival.restful.security.AuthenticationStrategy;
import com.github.yingzhuo.carnival.restful.security.annotation.AuthenticationComponent;
import com.github.yingzhuo.carnival.restful.security.annotation.IgnoreToken;
import com.github.yingzhuo.carnival.restful.security.blacklist.TokenBlacklistManager;
import com.github.yingzhuo.carnival.restful.security.exception.TokenBlacklistedException;
import com.github.yingzhuo.carnival.restful.security.exception.TokenNotWhitelistedException;
import com.github.yingzhuo.carnival.restful.security.parser.TokenParser;
import com.github.yingzhuo.carnival.restful.security.realm.UserDetailsRealm;
import com.github.yingzhuo.carnival.restful.security.token.Token;
import com.github.yingzhuo.carnival.restful.security.userdetails.UserDetails;
import com.github.yingzhuo.carnival.restful.security.whitelist.TokenWhitelistManager;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Optional;

/**
 * @author 应卓
 * @since 1.6.22
 */
interface RestfulSecurity {

    @SuppressWarnings({"unchecked", "rawtypes"})
    public default void doExecute(HttpServletRequest request, HttpServletResponse response, HandlerMethod handlerMethod) {

        final AuthenticationStrategy authenticationStrategy = getAuthenticationStrategy();
        final TokenParser tokenParser = getTokenParser();
        final UserDetailsRealm userDetailsRealm = getUserDetailsRealm();
        final TokenWhitelistManager tokenWhitelistManager = getTokenWhitelistManager();
        final TokenBlacklistManager tokenBlacklistManager = getTokenBlacklistManager();

        if (handlerMethod == null) {
            return;
        }

        if (handlerMethod.hasMethodAnnotation(IgnoreToken.class) || handlerMethod.getBeanType().getAnnotation(IgnoreToken.class) != null) {
            return;
        }

        final List<MethodCheckPoint> list = ReflectCache.get().get(handlerMethod.getMethod());
        if ((list == null || list.isEmpty()) && authenticationStrategy == AuthenticationStrategy.ANNOTATED_REQUESTS) {
            return;
        }

        final Optional<Token> tokenOp = tokenParser.parse(new ServletWebRequest(request, response));
        if (tokenOp.isPresent()) {
            Token token = tokenOp.get();
            RestfulSecurityContext.setToken(token);

            if (tokenBlacklistManager != null && tokenBlacklistManager.isBlacklisted(token)) {
                throw new TokenBlacklistedException(request, token);
            }

            Optional<UserDetails> userDetailsOp = userDetailsRealm.loadUserDetails(tokenOp.get());
            RestfulSecurityContext.setUserDetails(userDetailsOp.orElse(null));
        }

        if (list != null) {
            list.forEach(cp -> {
                Annotation annotation = cp.getAnnotation();
                AuthenticationComponent ac = cp.getAuthenticationComponent();
                ac.authenticate(RestfulSecurityContext.getToken().orElse(null), RestfulSecurityContext.getUserDetails().orElse(null), annotation);
            });
        }

        if (tokenWhitelistManager != null) {
            if (!tokenWhitelistManager.isWhitelisted(RestfulSecurityContext.getToken().orElse(null),
                    RestfulSecurityContext.getUserDetails().orElse(null))) {
                throw new TokenNotWhitelistedException(
                        request,
                        RestfulSecurityContext.getToken().orElse(null),
                        RestfulSecurityContext.getUserDetails().orElse(null)
                );
            }
        }
    }

    public AuthenticationStrategy getAuthenticationStrategy();

    public TokenParser getTokenParser();

    public UserDetailsRealm getUserDetailsRealm();

    public TokenBlacklistManager getTokenBlacklistManager();

    public TokenWhitelistManager getTokenWhitelistManager();

}
