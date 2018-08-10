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
import com.github.yingzhuo.carnival.restful.security.autoconfig.RestfulSecurityAutoConfig;
import com.github.yingzhuo.carnival.restful.security.autoconfig.RestfulSecurityInterceptorAutoConfig;
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
@Import(EnableRestfulSecurity.ImportSelector.class)
public @interface EnableRestfulSecurity {

    public int interceptorOrder() default 0;

    public AuthenticationStrategy authenticationStrategy() default AuthenticationStrategy.ALL;

    public static class ImportSelector extends AbstractImportSelector {

        @Override
        public String[] selectImports(AnnotationMetadata importingClassMetadata) {

            AnnotationAttributes aas = super.getAnnotationAttributes(importingClassMetadata, EnableRestfulSecurity.class);

            Integer interceptorOrder = aas.getNumber("interceptorOrder");
            AuthenticationStrategy authenticationStrategy = aas.getEnum("authenticationStrategy");

            putConfig("interceptorOrder", interceptorOrder);
            putConfig("authenticationStrategy", authenticationStrategy);

            return new String[]{
                    RestfulSecurityAutoConfig.class.getName(),
                    RestfulSecurityInterceptorAutoConfig.class.getName()
            };
        }
    }

}
