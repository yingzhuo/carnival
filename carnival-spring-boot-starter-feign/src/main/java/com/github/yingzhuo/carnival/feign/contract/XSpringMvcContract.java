/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.feign.contract;

import com.github.yingzhuo.carnival.feign.XRequestHeader;
import com.github.yingzhuo.carnival.feign.XRequestHeaderHttpBasic;
import feign.MethodMetadata;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.http.HttpHeaders;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author 应卓
 * @since 1.6.0
 */
public class XSpringMvcContract extends SpringMvcContract {

    @Override
    protected void processAnnotationOnClass(MethodMetadata data, Class<?> clz) {
        super.processAnnotationOnClass(data, clz);
        doProcessAnnotationOnClass(data, clz);
    }

    @Override
    protected void processAnnotationOnMethod(MethodMetadata data, Annotation methodAnnotation, Method method) {
        super.processAnnotationOnMethod(data, methodAnnotation, method);
        doProcessAnnotationOnMethod(data, methodAnnotation, method);
    }

    @Override
    protected boolean processAnnotationsOnParameter(MethodMetadata data, Annotation[] annotations, int paramIndex) {
        final boolean isHttp = super.processAnnotationsOnParameter(data, annotations, paramIndex);
        doProcessAnnotationsOnParameter(data, annotations, paramIndex);
        return isHttp;
    }

    // ----------------------------------------------------------------------------------------------------------------

    private void doProcessAnnotationOnClass(MethodMetadata data, Class<?> clz) {
    }

    private void doProcessAnnotationOnMethod(MethodMetadata data, Annotation annotation, Method method) {

        if (XRequestHeader.class.isInstance(annotation)) {
            XRequestHeader a = method.getAnnotation(XRequestHeader.class);
            data.template().header(a.name(), a.value());
        }

        if (XRequestHeaderHttpBasic.class.isInstance(annotation)) {
            XRequestHeaderHttpBasic a = method.getAnnotation(XRequestHeaderHttpBasic.class);
            final String username = a.username();
            final String password = a.password();
            final String value = "Basic " + Base64.getUrlEncoder().encodeToString(String.format("%s:%s", username, password).getBytes(StandardCharsets.UTF_8));
            data.template().header(HttpHeaders.AUTHORIZATION, value);
        }
    }

    private void doProcessAnnotationsOnParameter(MethodMetadata data, Annotation[] annotations, int paramIndex) {
    }

}
