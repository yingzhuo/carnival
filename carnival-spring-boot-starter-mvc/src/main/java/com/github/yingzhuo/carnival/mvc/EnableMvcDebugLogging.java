/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.mvc;

import com.github.yingzhuo.carnival.common.autoconfig.support.AnnotationAttributesHolder;
import com.github.yingzhuo.carnival.mvc.autoconfig.MvcDebugAutoConfig;
import org.springframework.context.annotation.Import;
import org.springframework.core.type.AnnotationMetadata;

import java.lang.annotation.*;

/**
 * @author 应卓
 */
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(EnableMvcDebugLogging.ImportSelector.class)
public @interface EnableMvcDebugLogging {

    public MvcDebugLoggingImpl impl() default MvcDebugLoggingImpl.INTERCEPTOR;

    public static class ImportSelector implements org.springframework.context.annotation.ImportSelector {

        @Override
        public String[] selectImports(AnnotationMetadata importingClassMetadata) {
            AnnotationAttributesHolder.setAnnotationMetadata(EnableMvcDebugLogging.class, importingClassMetadata);

            return new String[]{
                    MvcDebugAutoConfig.class.getName()
            };
        }
    }
}
