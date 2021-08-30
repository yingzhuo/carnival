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
import com.github.yingzhuo.carnival.graphql.annotation.OperationName;
import com.github.yingzhuo.carnival.graphql.request.Variables;
import com.github.yingzhuo.carnival.spring.RequestMappingUtils;
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
    private final List<OperationName> operationNameAnnotations;
    private final Object bean;
    private final Method method;
    private final int parameterCount;

    public ReflectionDataFetcher(List<Argument> argumentAnnotations,
                                 List<OperationName> operationNameAnnotations,
                                 Object bean,
                                 Method method) {

        this.argumentAnnotations = argumentAnnotations;
        this.operationNameAnnotations = operationNameAnnotations;
        this.bean = bean;
        this.method = method;
        this.parameterCount = method.getParameterCount();
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

            if (method.getParameterTypes()[i] == Variables.class) {
                params.add(InvokeContext.VariablesHolder.get());
                continue;
            }

            Argument argumentAnnotation = argumentAnnotations.get(i);
            OperationName operationNameAnnotation = operationNameAnnotations.get(i);

            // 参数上有 @Argument
            if (argumentAnnotation != null) {
                params.add(getParameterFromDataFetchingEnvironment(env, argumentAnnotation));
                continue;
            }

            // 参数上有 @OperationName
            if (operationNameAnnotation != null) {
                params.add(InvokeContext.OperationNameHolder.get());
                continue;
            }

            params.add(getParameterFromHandlerMethodArgumentResolvers());
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
        for (HandlerMethodArgumentResolver resolver : RequestMappingUtils.getHandlerMethodArgumentResolvers()) {
            if (resolver.supportsParameter(InvokeContext.MethodParameterHolder.get())) {
                return resolver.resolveArgument(
                        InvokeContext.MethodParameterHolder.get(),
                        InvokeContext.ModelAndViewContainerHolder.get(),
                        InvokeContext.NativeWebRequestHolder.get(),
                        InvokeContext.WebDataBinderFactoryHolder.get()
                );
            }
        }
        return null;
    }

}
