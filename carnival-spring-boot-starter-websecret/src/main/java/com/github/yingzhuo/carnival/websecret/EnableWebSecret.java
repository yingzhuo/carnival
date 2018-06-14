/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.websecret;

import com.github.yingzhuo.carnival.common.autoconfig.support.AbstractImportSelector;
import com.github.yingzhuo.carnival.websecret.autoconfig.WebSecretConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 应卓
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(EnableWebSecret.WebSecretImportSelector.class)
public @interface EnableWebSecret {

    public int interceptorOrder() default 0;

    public static class WebSecretImportSelector extends AbstractImportSelector {

        @Override
        public String[] selectImports(AnnotationMetadata importingClassMetadata) {

            AnnotationAttributes aas = super.getAnnotationAttributes(importingClassMetadata, EnableWebSecret.class);

            Integer order = aas.getNumber("interceptorOrder");
            putConfig("order", order);

            return new String[]{
                    WebSecretConfiguration.class.getName()
            };
        }
    }

}
