/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.graphql.autoconfig;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.yingzhuo.carnival.graphql.core.GraphQLController;
import com.github.yingzhuo.carnival.graphql.core.GraphQLFactoryBean;
import com.github.yingzhuo.carnival.graphql.schema.SchemaText;
import com.github.yingzhuo.carnival.graphql.schema.SchemaTextBuilder;
import com.github.yingzhuo.carnival.graphql.servlet.*;
import graphql.GraphQL;
import org.dataloader.DataLoaderRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ResourceLoader;

/**
 * @author 应卓
 * @since 1.10.14
 */
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class GraphQLAutoConfig {

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired(required = false)
    private DataLoaderRegistry dataLoaderRegistry;

    @Bean
    @ConditionalOnMissingBean
    JsonSerializer jsonSerializer(ObjectMapper objectMapper) {
        return new JacksonJsonSerializer(objectMapper);
    }

    @Bean
    @ConditionalOnMissingBean
    SchemaText schemaText() {
        final SchemaTextBuilder builder = SchemaText.builder();
        for (String location : ConfigHolder.schemaLocations) {
            builder.append(resourceLoader.getResource(location), ConfigHolder.schemaCharset);
        }
        return builder.build();
    }

    @Bean
    @ConditionalOnMissingBean
    ExecutionInputCustomizer executionInputCustomizer() {
        return new DefaultExecutionInputCustomizer();
    }

    @Bean
    @ConditionalOnMissingBean
    ExecutionResultHandler executionResultHandler() {
        return new DefaultExecutionResultHandler();
    }

    @Bean
    @ConditionalOnMissingBean(GraphQL.class)
    public GraphQLFactoryBean graphQL(SchemaText schemaText) {
        return new GraphQLFactoryBean(schemaText);
    }

    @Bean
    @ConditionalOnMissingBean
    GraphQLInvocation graphQLInvocation(GraphQL graphQL, ExecutionInputCustomizer executionInputCustomizer) {
        return new DefaultGraphQLInvocation(graphQL, executionInputCustomizer, dataLoaderRegistry);
    }

    @Bean
    @ConditionalOnMissingBean
    GraphQLController graphQLController(GraphQLInvocation graphQLInvocation, ExecutionResultHandler executionResultHandler, JsonSerializer jsonSerializer) {
        return new GraphQLController(graphQLInvocation, executionResultHandler, jsonSerializer);
    }

}
