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

import com.github.yingzhuo.carnival.openfeign.SpringQueryMap;
import feign.MethodMetadata;
import feign.QueryMap;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * {@link QueryMap} parameter processor.
 * {@link SpringQueryMap} parameter processor.
 *
 * @author 应卓
 * @since 1.6.23
 */
public class SpringQueryMapParameterProcessor implements AnnotatedParameterProcessor {

    private static final Class<SpringQueryMap> ANNOTATION = SpringQueryMap.class;

    @Override
    public Class<? extends Annotation> getAnnotationType() {
        return ANNOTATION;
    }

    @Override
    public boolean processArgument(AnnotatedParameterContext context,
                                   Annotation annotation, Method method) {
        int paramIndex = context.getParameterIndex();
        MethodMetadata metadata = context.getMethodMetadata();
        if (metadata.queryMapIndex() == null) {
            metadata.queryMapIndex(paramIndex);
            metadata.queryMapEncoded(ANNOTATION.cast(annotation).encoded());
        }
        return true;
    }

}
