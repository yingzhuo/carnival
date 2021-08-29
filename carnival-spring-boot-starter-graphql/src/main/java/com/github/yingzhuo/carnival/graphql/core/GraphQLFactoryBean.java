/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.graphql.core;

import com.github.yingzhuo.carnival.graphql.schema.SchemaText;
import graphql.GraphQL;
import org.springframework.beans.factory.FactoryBean;

/**
 * @author 应卓
 * @since 1.10.14
 */
public class GraphQLFactoryBean extends AbstractGraphQLFactoryBean implements FactoryBean<GraphQL> {

    private final SchemaText schemaText;

    public GraphQLFactoryBean(SchemaText schemaText) {
        this.schemaText = schemaText;
    }

    @Override
    public GraphQL getObject() {
        return GraphQL.newGraphQL(buildSchema(schemaText.getText()))
                .build();
    }

    @Override
    public Class<?> getObjectType() {
        return GraphQL.class;
    }

}
