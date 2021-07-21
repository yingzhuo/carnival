/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.mvc.cors;

import com.github.yingzhuo.carnival.spring.AnnotationAttributesUtils;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author 应卓
 * @since 1.9.14
 */
class EnableCorsFilterImportSelector implements ImportSelector {

    @Override
    @SuppressWarnings("NullableProblems")
    public String[] selectImports(AnnotationMetadata metadata) {
        final AnnotationAttributes attributes =
                AnnotationAttributesUtils.toAnnotationAttributes(metadata, EnableCorsFilter.class);

        CorsFilterAutoConfiguration.defaultOrder = attributes.getNumber("order");

        return new String[]{CorsFilterAutoConfiguration.class.getName()};
    }

}
