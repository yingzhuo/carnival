/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.graphql.core;

import com.github.yingzhuo.carnival.graphql.annotation.Argument;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 应卓
 * @since 1.10.14
 */
class ReflectionDataFetcher implements DataFetcher<Object> {

    private final List<Argument> argumentAnnotations;
    private final Object bean;
    private final Method method;
    private final List<HandlerMethodArgumentResolver> handlerMethodArgumentResolvers;
    private final int parameterCount;

    public ReflectionDataFetcher(List<Argument> argumentAnnotations,
                                 Object bean,
                                 Method method,
                                 List<HandlerMethodArgumentResolver> handlerMethodArgumentResolvers) {

        this.argumentAnnotations = argumentAnnotations;
        this.bean = bean;
        this.method = method;
        this.parameterCount = method.getParameterCount();
        this.handlerMethodArgumentResolvers = handlerMethodArgumentResolvers;
        this.method.setAccessible(true);
    }

    @Override
    public Object get(DataFetchingEnvironment env) throws Exception {

        // 无参数方法
        if (parameterCount == 0) {
            return method.invoke(bean);
        } else {
            return method.invoke(bean, getParameter(env));
        }
    }

    private Object[] getParameter(DataFetchingEnvironment env) throws Exception {
        final List<Object> params = new ArrayList<>(parameterCount);

        for (int i = 0; i < parameterCount; i++) {
            Argument argumentAnnotation = argumentAnnotations.get(i);
            if (argumentAnnotation != null) {
                params.add(getParameterFromDataFetchingEnvironment(env, argumentAnnotation));
            } else {
                params.add(getParameterFromHandlerMethodArgumentResolvers());
            }
        }

        return params.toArray();
    }

    private Object getParameterFromDataFetchingEnvironment(DataFetchingEnvironment env, Argument argumentAnnotation)
            throws Exception {
        final String paramName = argumentAnnotation.value();
        return env.getArgument(paramName);
    }

    private Object getParameterFromHandlerMethodArgumentResolvers()
            throws Exception {
        for (HandlerMethodArgumentResolver resolver : handlerMethodArgumentResolvers) {
            if (resolver.supportsParameter(InvokeContext.METHOD_PARAMETER_HOLDER.get())) {
                return resolver.resolveArgument(
                        InvokeContext.METHOD_PARAMETER_HOLDER.get(),
                        InvokeContext.MAV_CONTAINER_HOLDER.get(),
                        InvokeContext.REQUEST_HOLDER.get(),
                        InvokeContext.WEB_DATA_BINDER_FACTORY_HOLDER.get()
                );
            }
        }
        return null;
    }

}
