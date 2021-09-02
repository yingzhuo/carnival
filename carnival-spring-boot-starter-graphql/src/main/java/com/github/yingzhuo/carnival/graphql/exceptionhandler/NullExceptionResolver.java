/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.graphql.exceptionhandler;

import graphql.GraphQLError;
import graphql.schema.DataFetchingEnvironment;

import java.util.Collections;
import java.util.List;

/**
 * @author 应卓
 * @since 1.10.14
 */
public final class NullExceptionResolver implements ExceptionResolver {

    public static final NullExceptionResolver INSTANCE = new NullExceptionResolver();

    private NullExceptionResolver() {
    }

    @Override
    public boolean support(Throwable exception) {
        return false;
    }

    @Override
    public List<GraphQLError> resolve(Throwable exception, DataFetchingEnvironment environment) {
        return Collections.emptyList();
    }

}
