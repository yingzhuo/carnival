/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.openfeign.contract;

import feign.MethodMetadata;
import org.springframework.web.bind.annotation.RequestPart;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collection;

import static feign.Util.checkState;
import static feign.Util.emptyToNull;

public class RequestPartParameterProcessor implements AnnotatedParameterProcessor {

    private static final Class<RequestPart> ANNOTATION = RequestPart.class;

    @Override
    public Class<? extends Annotation> getAnnotationType() {
        return ANNOTATION;
    }

    @Override
    public boolean processArgument(AnnotatedParameterContext context,
                                   Annotation annotation, Method method) {
        int parameterIndex = context.getParameterIndex();
        MethodMetadata data = context.getMethodMetadata();

        String name = ANNOTATION.cast(annotation).value();
        checkState(emptyToNull(name) != null,
                "RequestPart.value() was empty on parameter %s", parameterIndex);
        context.setParameterName(name);

        data.formParams().add(name);
        Collection<String> names = context.setTemplateParameter(name,
                data.indexToName().get(parameterIndex));
        data.indexToName().put(parameterIndex, names);
        return true;
    }

}
