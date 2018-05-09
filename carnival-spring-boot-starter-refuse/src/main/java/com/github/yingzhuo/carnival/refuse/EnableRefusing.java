/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.refuse;

import com.github.yingzhuo.carnival.refuse.autoconfig.RefusingBeanConfiguration;
import com.github.yingzhuo.carnival.refuse.autoconfig.RefusingCoreConfiguration;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.context.annotation.Import;
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
@Import(EnableRefusing.ImportSelector.class)
public @interface EnableRefusing {

    static final class ImportSelector implements DeferredImportSelector {
        @Override
        public String[] selectImports(AnnotationMetadata metadata) {
            return new String[]{
                    RefusingBeanConfiguration.class.getName(),
                    RefusingCoreConfiguration.class.getName()
            };
        }
    }

}
