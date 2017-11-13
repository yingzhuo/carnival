/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.refuse.autoconfig;

import com.github.yingzhuo.carnival.refuse.RefuseConfigLoader;
import com.github.yingzhuo.carnival.refuse.RefuseContext;
import com.github.yingzhuo.carnival.refuse.RefuseListener;
import com.github.yingzhuo.carnival.refuse.RefusedException;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

/**
 * @author 应卓
 */
@ConditionalOnWebApplication
@AutoConfigureAfter(RefuseBeanConfiguration.class)
public class RefuseCoreConfiguration extends WebMvcConfigurerAdapter {

    private final RefuseConfigLoader loader;
    private final RefuseListener listener;

    public RefuseCoreConfiguration(RefuseConfigLoader loader, RefuseListener listener) {
        this.loader = loader;
        this.listener = listener;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RefuseInterceptor(loader, listener)).addPathPatterns("/", "/**");
    }

    private static class RefuseInterceptor extends HandlerInterceptorAdapter {
        private final PathMatcher pathMatcher = new AntPathMatcher();
        private RefuseConfigLoader loader;
        private RefuseListener listener;

        public RefuseInterceptor(RefuseConfigLoader loader, RefuseListener listener) {
            this.loader = loader;
            this.listener = listener;
        }

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
            Map<String, String> config = loader.load();
            String path = request.getRequestURI();

            config.keySet().forEach(pattern -> {
                if (pathMatcher.match(pattern, path)) {
                    listener.execute(new RefuseContext(new Date(), path, (HandlerMethod) handler));
                    throw new RefusedException(config.get(pattern));
                }
            });

            return true;
        }
    }

}
