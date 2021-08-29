/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.graphql;

import com.github.yingzhuo.carnival.graphql.autoconfig.ConfigHolder;
import com.github.yingzhuo.carnival.graphql.autoconfig.GraphQLAutoConfig;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author 应卓
 * @since 1.10.14
 */
class EnableGraphQLImportSelector implements ImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata importing) {
        final AnnotationAttributes attributes = AnnotationAttributes.fromMap(
                importing.getAnnotationAttributes(EnableGraphQL.class.getName())
        );

        ConfigHolder.url = attributes.getString("url");
        ConfigHolder.sdl = attributes.getStringArray("sdl");

        System.setProperty("graphql.url", ConfigHolder.url);

        return new String[]{
                GraphQLAutoConfig.class.getName()
        };
    }

}
