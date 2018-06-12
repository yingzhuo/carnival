/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.exception;

import com.github.yingzhuo.carnival.common.autoconfig.support.AbstractImportSelector;
import com.github.yingzhuo.carnival.exception.autoconfig.BusinessExceptionFactoryConfiguration;
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
@Import(EnableBusinessExceptionFactory.ImportSelector.class)
public @interface EnableBusinessExceptionFactory {

    public String location() default "classpath:/business-exception.ini";

    public static class ImportSelector extends AbstractImportSelector {

        @Override
        public String[] selectImports(AnnotationMetadata importingClassMetadata) {
            AnnotationAttributes aas = getAnnotationAttributes(importingClassMetadata, EnableBusinessExceptionFactory.class);
            ImportSelector.putConfig("location", aas.getString("location"));

            return new String[]{
                    BusinessExceptionFactoryConfiguration.class.getName()
            };
        }
    }

}
