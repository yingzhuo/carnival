/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.security;

import com.github.yingzhuo.carnival.common.autoconfig.support.AbstractImportSelector;
import com.github.yingzhuo.carnival.restful.security.autoconfig.RestfulSecurityBeanConfiguration;
import com.github.yingzhuo.carnival.restful.security.autoconfig.RestfulSecurityInterceptorConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

import java.lang.annotation.*;

@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(EnableRestfulSecurity.ImportSelector.class)
public @interface EnableRestfulSecurity {

    public int interceptorOrder() default 0;

    public static class ImportSelector extends AbstractImportSelector {

        @Override
        public String[] selectImports(AnnotationMetadata importingClassMetadata) {

            AnnotationAttributes aas = super.getAnnotationAttributes(importingClassMetadata, EnableRestfulSecurity.class);

            Integer order = aas.getNumber("interceptorOrder");
            putConfig("order", order);

            return new String[]{
                    RestfulSecurityBeanConfiguration.class.getName(),
                    RestfulSecurityInterceptorConfiguration.class.getName()
            };
        }
    }

}
