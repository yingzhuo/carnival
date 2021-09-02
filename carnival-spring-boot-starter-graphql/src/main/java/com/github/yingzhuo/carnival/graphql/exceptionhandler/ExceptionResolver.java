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
import org.springframework.core.Ordered;

import java.util.List;

/**
 * @author 应卓
 * @since 1.10.14
 */
public interface ExceptionResolver extends Ordered {

    public boolean support(Throwable exception);

    public List<GraphQLError> resolve(Throwable exception, DataFetchingEnvironment environment);

    @Override
    public default int getOrder() {
        return 0;
    }

}
