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

import com.github.yingzhuo.carnival.common.mvc.AbstractHandlerInterceptorSupport;
import com.github.yingzhuo.carnival.nonce.*;
import com.github.yingzhuo.carnival.nonce.exception.NonceTokenException;
import com.github.yingzhuo.carnival.nonce.impl.NoopNonceTokenDao;
import com.github.yingzhuo.carnival.nonce.impl.UUIDNonceTokenGenerator;
import com.github.yingzhuo.carnival.nonce.impl.SmartNonceTokenResolver;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 应卓
 * @since 1.6.29
 */
@EnableConfigurationProperties(NonceAutoConfig.NonceProps.class)
@ConditionalOnProperty(prefix = "carnival.nonce", name = "enabled", havingValue = "true", matchIfMissing = true)
@ConditionalOnWebApplication
public class NonceAutoConfig implements WebMvcConfigurer {

    @Autowired(required = false)
    private NonceTokenResolver resolver = new SmartNonceTokenResolver();

    @Autowired(required = false)
    private NonceTokenDao dao = new NoopNonceTokenDao();

    @Bean
    @ConditionalOnMissingBean
    public NonceTokenGenerator nonceTokenGenerator() {
        return new UUIDNonceTokenGenerator();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new NonceInterceptor(resolver, dao));
    }

    // ----------------------------------------------------------------------------------------------------------------
    private static class NonceInterceptor extends AbstractHandlerInterceptorSupport {
        private final NonceTokenResolver resolver;
        private final NonceTokenDao dao;

        public NonceInterceptor(NonceTokenResolver resolver, NonceTokenDao dao) {
            this.resolver = resolver;
            this.dao = dao;
        }

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
            if (!(handler instanceof HandlerMethod)) {
                return true;
            }

            if (super.hasMethodOrClassAnnotation(RequiresNonceToken.class, handler)) {
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
            return true;
        }
    }

    @Getter
    @Setter
    @ConfigurationProperties(prefix = "carnival.nonce")
    static class NonceProps {
        private boolean enabled = true;
    }

}
