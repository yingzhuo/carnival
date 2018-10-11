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
import com.github.yingzhuo.carnival.restful.security.annotation.Requires;
import com.github.yingzhuo.carnival.restful.security.blacklist.TokenBlackList;
import com.github.yingzhuo.carnival.restful.security.cache.CacheManager;
import com.github.yingzhuo.carnival.restful.security.chain.ChainNode;
import com.github.yingzhuo.carnival.restful.security.exception.TokenBlacklistedException;
import com.github.yingzhuo.carnival.restful.security.listener.AuthenticationListener;
import com.github.yingzhuo.carnival.restful.security.token.Token;
import com.github.yingzhuo.carnival.restful.security.userdetails.UserDetails;
import com.github.yingzhuo.carnival.restful.security.voter.AuthenticationResult;
import com.github.yingzhuo.carnival.restful.security.voter.SimpleAuthenticationResult;
import com.github.yingzhuo.carnival.restful.security.voter.UserDetailsVoter;
import com.github.yingzhuo.carnival.spring.SpringUtils;
import lombok.val;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.FixedLocaleResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author 应卓
 */
public class RestfulSecurityChainInterceptor implements HandlerInterceptor {

    private Map<Method, List<MethodCheckPoint>> cache = new HashMap<>();

    private List<ChainNode> chainNodes;
    private UserDetailsVoter userDetailsVoter;

    private AuthenticationListener authenticationListener;
    private CacheManager cacheManager;
    private LocaleResolver localeResolver = new FixedLocaleResolver();
    private AuthenticationStrategy authenticationStrategy = AuthenticationStrategy.ONLY_ANNOTATED;
    private TokenBlackList tokenBlackList;
    private boolean initialized = false;

    public RestfulSecurityChainInterceptor() {
        super();
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (!initialized) {
            this.initCache(SpringUtils.getBean(RequestMappingHandlerMapping.class).getHandlerMethods().values());
        }

        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        val handlerMethod = (HandlerMethod) handler;

        final List<MethodCheckPoint> list = cache.get(handlerMethod.getMethod());
        if ((list == null || list.isEmpty()) && authenticationStrategy == AuthenticationStrategy.ONLY_ANNOTATED) {
            return true;
        }

        val results = getAuthenticationResults(request, response, handler);
        val op = userDetailsVoter.vote(results);

        RestfulSecurityContext.setUserDetails(op.orElse(null));

        if (list != null && !list.isEmpty()) {

            list.forEach(cp -> {
                Annotation annotation = cp.getAnnotation();
                AuthenticationComponent ac = cp.getAuthenticationComponent();
                ac.authenticate(RestfulSecurityContext.getUserDetails().orElse(null), annotation);
            });

        }

        return true;
    }

    private Method getMethod(Object handler) {
        return ((HandlerMethod) handler).getMethod();
    }

    // lazy
    private synchronized void initCache(Collection<HandlerMethod> handlerMethods) {
        if (!initialized) {

            for (HandlerMethod hm : handlerMethods) {
                final Method method = hm.getMethod();
                final List<MethodCheckPoint> list = new LinkedList<>();

                for (Annotation annotation : method.getDeclaredAnnotations()) {
                    Requires requires = annotation.annotationType().getAnnotation(Requires.class);
                    if (requires != null) {

                        final MethodCheckPoint checkPoint = new MethodCheckPoint(
                                annotation,
                                SpringUtils.getBean(requires.value())
                        );

                        list.add(checkPoint);
                    }
                }

                if (!list.isEmpty()) {
                    cache.put(method, list);
                }
            }

            this.initialized = true;
        }
    }

    public void setChainNodes(List<ChainNode> chainNodes) {
        this.chainNodes = chainNodes;
    }

    public void setAuthenticationListener(AuthenticationListener authenticationListener) {
        this.authenticationListener = authenticationListener;
    }

    public void setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public void setLocaleResolver(LocaleResolver localeResolver) {
        this.localeResolver = localeResolver;
    }

    public void setAuthenticationStrategy(AuthenticationStrategy authenticationStrategy) {
        this.authenticationStrategy = authenticationStrategy;
    }

    public void setTokenBlackList(TokenBlackList tokenBlackList) {
        this.tokenBlackList = tokenBlackList;
    }

    public void setUserDetailsVoter(UserDetailsVoter userDetailsVoter) {
        this.userDetailsVoter = userDetailsVoter;
    }

    // ----------------------------------------------------------------------------------------------------------------

    private List<AuthenticationResult> getAuthenticationResults(HttpServletRequest request, HttpServletResponse response, Object handler) {
        val results = new LinkedList<AuthenticationResult>();

        int index = -1;
        for (ChainNode node : chainNodes) {
            index++;

            val tokenParser = node.getTokenParser();
            val userDetailsRealm = node.getUserDetailsRealm();

            try {

                val locale = localeResolver.resolveLocale(request);
                val tokenOp = tokenParser.parse(new ServletWebRequest(request, response), locale);

                if (tokenOp.isPresent()) {

                    Token token = tokenOp.get();
                    RestfulSecurityContext.setToken(token);

                    if (tokenBlackList.isBlacklisted(token)) {
                        throw new TokenBlacklistedException();
                    }

                    Optional<UserDetails> userDetailsOp;
                    Optional<UserDetails> cached = cacheManager.getUserDetails(token);

                    if (cached.isPresent()) {
                        userDetailsOp = cached;
                    } else {
                        userDetailsOp = userDetailsRealm.loadUserDetails(tokenOp.get());
                        userDetailsOp.ifPresent(ud -> cacheManager.saveUserDetails(token, ud));
                    }

                    userDetailsOp.ifPresent(userDetails -> authenticationListener.onAuthenticated(new ServletWebRequest(request), userDetails, getMethod(handler)));
                    RestfulSecurityContext.setUserDetails(userDetailsOp.orElse(null));

                    results.add(
                            new SimpleAuthenticationResult(
                                    null,
                                    RestfulSecurityContext.getUserDetails().orElse(null),
                                    RestfulSecurityContext.getToken().orElse(null),
                                    index)
                    );
                } else {
                    results.add(
                            new SimpleAuthenticationResult(null, null, null, index));
                }

            } catch (Throwable ex) {
                results.add(new SimpleAuthenticationResult(ex, null, null, index));
            }
        }

        return results;
    }

}
