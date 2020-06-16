package com.github.yingzhuo.carnival.openfeign.contract;

import feign.MethodMetadata;
import feign.QueryMap;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * {@link QueryMap} parameter processor.
 *
 * @author Aram Peres
 * @see AnnotatedParameterProcessor
 */
public class QueryMapParameterProcessor implements AnnotatedParameterProcessor {

    private static final Class<QueryMap> ANNOTATION = QueryMap.class;

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
            metadata.queryMapEncoded(QueryMap.class.cast(annotation).encoded());
        }
        return true;
    }

}
