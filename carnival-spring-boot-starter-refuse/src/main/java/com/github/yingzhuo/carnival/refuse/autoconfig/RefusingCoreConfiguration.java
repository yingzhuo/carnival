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

import com.github.yingzhuo.carnival.refuse.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

/**
 * @author 应卓
 */
@ConditionalOnWebApplication
@AutoConfigureAfter(RefusingBeanConfiguration.class)
@Slf4j
public class RefusingCoreConfiguration extends WebMvcConfigurerAdapter {

    private final RefusingConfigLoader loader;
    private final RefusingListener listener;

    public RefusingCoreConfiguration(RefusingConfigLoader loader, RefusingListener listener) {
        this.loader = loader;
        this.listener = listener;
    }

    @PostConstruct
    private void init() {
        log.debug("SpringBoot auto-config: {}", getClass().getName());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RefuseInterceptor(loader, listener)).addPathPatterns("/", "/**");
    }

    private static class RefuseInterceptor extends HandlerInterceptorAdapter {
        private final PathMatcher pathMatcher = new AntPathMatcher();
        private RefusingConfigLoader loader;
        private RefusingListener listener;

        public RefuseInterceptor(RefusingConfigLoader loader, RefusingListener listener) {
            this.loader = loader;
            this.listener = listener;
        }

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
            RefusingConfig refusingConfig = loader.load();
            Map<String, String> config = refusingConfig.toMap();
            String path = request.getRequestURI();

            config.keySet().forEach(pattern -> {
                if (pathMatcher.match(pattern, path)) {
                    String reason = config.get(pattern);
                    if (!StringUtils.hasText(reason)) {
                        reason = refusingConfig.getDefaultReason();
                    }
                    listener.execute(new RefuseContext(new Date(), path, (HandlerMethod) handler, reason));
                    throw new AccessRefusedException(reason);
                }
            });

            return true;
        }
    }

}
