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

import com.github.yingzhuo.carnival.exception.business.BusinessException;
import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 应卓
 * @since 1.10.15
 */
public final class ExceptionHandlerUtils {

    private ExceptionHandlerUtils() {
    }

    public static List<GraphQLError> handle(
            final DataFetchingEnvironment environment,
            final ConstraintViolationException exception) {
        return handle(environment, exception, true);
    }

    public static List<GraphQLError> handle(
            final DataFetchingEnvironment environment,
            final ConstraintViolationException exception,
            final boolean joinMessage) {
        if (joinMessage) {
            final String msg = exception.getConstraintViolations()
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .sorted()
                    .collect(Collectors.joining(","));

            return Collections.singletonList(
                    GraphqlErrorBuilder
                            .newError(environment)
                            .errorType(ErrorType.BAD_REQUEST)
                            .message(msg)
                            .build()
            );
        } else {
            return exception.getConstraintViolations().stream()
                    .map(ConstraintViolation::getMessage)
                    .sorted()
                    .map(msg -> GraphqlErrorBuilder
                            .newError(environment)
                            .errorType(ErrorType.BAD_REQUEST)
                            .message(msg)
                            .build()
                    ).collect(Collectors.toList());
        }
    }

    public static List<GraphQLError> handle(
            final DataFetchingEnvironment environment,
            final BusinessException exception) {
        return Collections.singletonList(
                GraphqlErrorBuilder
                        .newError(environment)
                        .errorType(ErrorType.BUSINESS_ERROR)
                        .message(exception.getMessage())
                        .build()
        );
    }

}
