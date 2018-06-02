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

import com.github.yingzhuo.carnival.restful.security.autoconfig.RestfulSecurityBeanConfiguration;
import com.github.yingzhuo.carnival.restful.security.autoconfig.RestfulSecurityInterceptorConfiguration;
import org.springframework.context.annotation.DeferredImportSelector;
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

    public static class ImportSelector implements DeferredImportSelector {

        @Override
        public String[] selectImports(AnnotationMetadata importingClassMetadata) {

            AnnotationAttributes attributes = AnnotationAttributes.fromMap(
                    importingClassMetadata.getAnnotationAttributes(EnableRestfulSecurity.class.getName(), false));

            ImportSelectorConfigHolder.interceptorOrder = (Integer) attributes.get("interceptorOrder");

            return new String[]{
                    RestfulSecurityBeanConfiguration.class.getName(),
                    RestfulSecurityInterceptorConfiguration.class.getName()
            };
        }
    }

}
