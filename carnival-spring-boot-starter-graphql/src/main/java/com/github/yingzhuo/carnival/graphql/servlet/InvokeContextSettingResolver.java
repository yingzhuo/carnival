/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.graphql.servlet;

import com.github.yingzhuo.carnival.graphql.core.InvokeContext;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @author 应卓
 * @see InvokeContext
 * @since 1.10.14
 */
public final class InvokeContextSettingResolver implements HandlerMethodArgumentResolver {

    public static final InvokeContextSettingResolver INSTANCE = new InvokeContextSettingResolver();

    private InvokeContextSettingResolver() {
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(InvokeContext.class);
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory) {
        InvokeContext.METHOD_PARAMETER_HOLDER.set(parameter);
        InvokeContext.MAV_CONTAINER_HOLDER.set(mavContainer);
        InvokeContext.REQUEST_HOLDER.set(webRequest);
        InvokeContext.WEB_DATA_BINDER_FACTORY_HOLDER.set(binderFactory);
        return InvokeContext.INSTANCE;
    }

}
