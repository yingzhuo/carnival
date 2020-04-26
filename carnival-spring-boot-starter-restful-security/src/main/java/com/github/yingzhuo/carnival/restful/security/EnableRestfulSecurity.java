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

import com.github.yingzhuo.carnival.common.autoconfig.support.AnnotationAttributesHolder;
import com.github.yingzhuo.carnival.restful.security.autoconfig.RestfulSecurityBeanAutoConfig;
import com.github.yingzhuo.carnival.restful.security.autoconfig.RestfulSecurityCoreAutoConfig;
import org.springframework.context.annotation.Import;
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

    public AuthenticationStrategy authenticationStrategy() default AuthenticationStrategy.ANNOTATED_REQUESTS;

    public static class ImportSelector implements org.springframework.context.annotation.ImportSelector {

        @Override
        public String[] selectImports(AnnotationMetadata importingClassMetadata) {

            AnnotationAttributesHolder.setAnnotationMetadata(EnableRestfulSecurity.class, importingClassMetadata);

            return new String[]{
                    RestfulSecurityBeanAutoConfig.class.getName(),
                    RestfulSecurityCoreAutoConfig.class.getName()
            };
        }
    }

}
