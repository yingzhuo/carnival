/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.nonce.autoconfig;

import com.github.yingzhuo.carnival.nonce.*;
import com.github.yingzhuo.carnival.nonce.exception.NonceTokenException;
import com.github.yingzhuo.carnival.nonce.impl.NoopNonceTokenDao;
import com.github.yingzhuo.carnival.nonce.impl.RandomNonceTokenGenerator;
import com.github.yingzhuo.carnival.nonce.impl.SimpleNonceTokenResolver;
import com.github.yingzhuo.carnival.nonce.properties.NonceProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 应卓
 * @since 1.6.29
 */
@EnableConfigurationProperties(NonceProperties.class)
@ConditionalOnProperty(prefix = "carnival.nonce", name = "enabled", havingValue = "true", matchIfMissing = true)
@ConditionalOnWebApplication
public class NonceAutoConfig implements WebMvcConfigurer {

    @Autowired(required = false)
    private NonceTokenResolver resolver = new SimpleNonceTokenResolver();

    @Autowired(required = false)
    private NonceTokenDao dao = new NoopNonceTokenDao();

    @Bean
    @ConditionalOnMissingBean
    public NonceTokenGenerator nonceTokenGenerator() {
        return new RandomNonceTokenGenerator();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new NonceInterceptor(resolver, dao));
    }

    // ----------------------------------------------------------------------------------------------------------------
    private static class NonceInterceptor implements HandlerInterceptor {

        private final NonceTokenResolver resolver;
        private final NonceTokenDao dao;

        public NonceInterceptor(NonceTokenResolver resolver, NonceTokenDao dao) {
            this.resolver = resolver;
            this.dao = dao;
        }

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
            if (handler instanceof HandlerMethod) {
                if (((HandlerMethod) handler).hasMethodAnnotation(RequiresNonceToken.class)) {
                    NonceToken token = resolver.resolve(request);
                    if (token == null) {
                        throw new NonceTokenException("no nonce token", request);
                    }

                    if (!dao.exists(token)) {
                        throw new NonceTokenException("invalid nonce token", request);
                    } else {
                        dao.delete(token);
                    }
                }
            }
            return true;
        }
    }

}
