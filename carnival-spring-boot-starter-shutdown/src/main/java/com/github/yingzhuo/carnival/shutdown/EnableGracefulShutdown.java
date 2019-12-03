/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.shutdown;

import com.github.yingzhuo.carnival.common.autoconfig.support.AnnotationAttributesHolder;
import com.github.yingzhuo.carnival.shutdown.autoconfig.GracefulShutdownAutoConfig;
import org.springframework.context.annotation.Import;
import org.springframework.core.type.AnnotationMetadata;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * @author 应卓
 * @since 1.3.1
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(EnableGracefulShutdown.ImportSelector.class)
public @interface EnableGracefulShutdown {

    public long timeout() default 10L;

    public TimeUnit timeoutTimeUnit() default TimeUnit.SECONDS;

    public static class ImportSelector implements org.springframework.context.annotation.ImportSelector {

        @Override
        public String[] selectImports(AnnotationMetadata importingClassMetadata) {
            AnnotationAttributesHolder.setAnnotationMetadata(EnableGracefulShutdown.class, importingClassMetadata);
            return new String[]{GracefulShutdownAutoConfig.class.getName()};
        }
    }

}
