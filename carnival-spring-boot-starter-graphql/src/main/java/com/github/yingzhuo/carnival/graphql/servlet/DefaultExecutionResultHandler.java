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

import graphql.ExecutionResult;

import java.util.concurrent.CompletableFuture;

/**
 * @author 应卓
 */
public class DefaultExecutionResultHandler implements ExecutionResultHandler {

    @Override
    public Object handleExecutionResult(CompletableFuture<ExecutionResult> executionResultCF) {
        return executionResultCF.thenApply(ExecutionResult::toSpecification);
    }

}
