/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.shield.core;

import com.github.yingzhuo.carnival.shield.DecryptBody;
import com.github.yingzhuo.carnival.shield.EncryptBody;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @author 应卓
 * @since 1.6.21
 */
abstract class AbstractShieldFilter extends OncePerRequestFilter {

    private final RequestMappingHandlerMapping mappings;

    public AbstractShieldFilter(RequestMappingHandlerMapping mappings) {
        this.mappings = Objects.requireNonNull(mappings);
    }

    protected final HandlerMethod getHandlerMethod(HttpServletRequest request) {
        try {
            final HandlerExecutionChain handlerExecutionChain = mappings.getHandler(request);
            return (HandlerMethod) handlerExecutionChain.getHandler();
        } catch (Exception e) {
            return null;
        }
    }

    protected final boolean shouldEncrypt(HandlerMethod handlerMethod) {
        if (handlerMethod.hasMethodAnnotation(EncryptBody.class)) {
            return true;
        }
        return handlerMethod.getBeanType().getAnnotation(EncryptBody.class) != null;
    }

    protected final boolean shouldDecrypt(HandlerMethod handlerMethod) {
        if (handlerMethod.hasMethodAnnotation(DecryptBody.class)) {
            return true;
        }
        return handlerMethod.getBeanType().getAnnotation(DecryptBody.class) != null;
    }

}
