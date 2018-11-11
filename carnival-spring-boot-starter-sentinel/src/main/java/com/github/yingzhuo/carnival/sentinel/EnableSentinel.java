/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.sentinel;

import com.github.yingzhuo.carnival.common.autoconfig.support.AbstractImportSelector;
import com.github.yingzhuo.carnival.sentinel.autocnf.SentinelAutoCnf;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

import java.lang.annotation.*;

/**
 * @author 应卓
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(EnableSentinel.ImportSelector.class)
public @interface EnableSentinel {

    public int interceptorOrder() default 0;

    public static class ImportSelector extends AbstractImportSelector {

        @Override
        public String[] selectImports(AnnotationMetadata metadata) {

            final AnnotationAttributes attributes =
                    getAnnotationAttributes(metadata, EnableSentinel.class);

            int interceptorOrder = attributes.getNumber("interceptorOrder");
            putConfig("interceptorOrder", interceptorOrder);

            return new String[]{
                    SentinelAutoCnf.class.getName()
            };
        }
    }

}
