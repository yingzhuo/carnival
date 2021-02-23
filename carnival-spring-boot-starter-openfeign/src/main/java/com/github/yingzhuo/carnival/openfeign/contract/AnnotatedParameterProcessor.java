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

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collection;

/**
 * @since 1.6.17
 */
public interface AnnotatedParameterProcessor {

    /**
     * Retrieves the processor supported annotation type.
     *
     * @return the annotation type
     */
    Class<? extends Annotation> getAnnotationType();

    /**
     * Process the annotated parameter.
     *
     * @param context    the parameter context
     * @param annotation the annotation instance
     * @param method     the method that contains the annotation
     * @return whether the parameter is http
     */
    boolean processArgument(AnnotatedParameterContext context, Annotation annotation,
                            Method method);

    /**
     * Specifies the parameter context.
     *
     * @author Jakub Narloch
     */
    interface AnnotatedParameterContext {

        /**
         * Retrieves the method metadata.
         *
         * @return the method metadata
         */
        MethodMetadata getMethodMetadata();

        /**
         * Retrieves the index of the parameter.
         *
         * @return the parameter index
         */
        int getParameterIndex();

        /**
         * Sets the parameter name.
         *
         * @param name the name of the parameter
         */
        void setParameterName(String name);

        /**
         * Sets the template parameter.
         *
         * @param name the template parameter
         * @param rest the existing parameter values
         * @return parameters
         */
        Collection<String> setTemplateParameter(String name, Collection<String> rest);

    }

}