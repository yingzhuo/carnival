/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.graphql.servlet;

import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;
import org.dataloader.DataLoaderRegistry;
import org.springframework.web.context.request.WebRequest;

import java.util.concurrent.CompletableFuture;

/**
 * @author 应卓
 * @since 1.10.14
 */
public class DefaultGraphQLInvocation implements GraphQLInvocation {

    private final GraphQL graphQL;
    private final ExecutionInputCustomizer executionInputCustomizer;
    private final DataLoaderRegistry dataLoaderRegistry;

    public DefaultGraphQLInvocation(GraphQL graphQL, ExecutionInputCustomizer executionInputCustomizer) {
        this(graphQL, executionInputCustomizer, null);
    }

    public DefaultGraphQLInvocation(GraphQL graphQL, ExecutionInputCustomizer executionInputCustomizer, DataLoaderRegistry dataLoaderRegistry) {
        this.graphQL = graphQL;
        this.executionInputCustomizer = executionInputCustomizer;
        this.dataLoaderRegistry = dataLoaderRegistry;
    }

    @Override
    public CompletableFuture<ExecutionResult> invoke(GraphQLInvocationData invocationData, WebRequest webRequest) {
        ExecutionInput.Builder executionInputBuilder = ExecutionInput.newExecutionInput()
                .query(invocationData.getQuery())
                .operationName(invocationData.getOperationName())
                .variables(invocationData.getVariables());
        if (dataLoaderRegistry != null) {
            executionInputBuilder.dataLoaderRegistry(dataLoaderRegistry);
        }
        ExecutionInput executionInput = executionInputBuilder.build();
        CompletableFuture<ExecutionInput> customizedExecutionInput = executionInputCustomizer.customizeExecutionInput(executionInput, webRequest);
        return customizedExecutionInput.thenCompose(graphQL::executeAsync);
    }

}
