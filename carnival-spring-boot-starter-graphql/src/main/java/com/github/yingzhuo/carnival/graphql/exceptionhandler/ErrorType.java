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

import graphql.ErrorClassification;
import graphql.GraphQLError;

/**
 * @author 应卓
 * @see graphql.ErrorType
 * @see org.springframework.graphql.execution.ErrorType
 * @since 1.10.16
 */
public final class ErrorType {

    public static final ErrorClassification BAD_REQUEST = of("BAD_REQUEST");
    public static final ErrorClassification UNAUTHORIZED = of("UNAUTHORIZED");
    public static final ErrorClassification FORBIDDEN = of("FORBIDDEN");
    public static final ErrorClassification UNAUTHORIZED_OR_FORBIDDEN = of("UNAUTHORIZED_OR_FORBIDDEN");
    public static final ErrorClassification INTERNAL_ERROR = of("INTERNAL_ERROR");
    public static final ErrorClassification OPERATION_NOT_SUPPORTED = of("OPERATION_NOT_SUPPORTED");
    public static final ErrorClassification BUSINESS_ERROR = of("BUSINESS_ERROR");

    private ErrorType() {
    }

    public static ErrorClassification of(final String type) {
        return new ErrorClassification() {
            @Override
            public Object toSpecification(GraphQLError error) {
                return type;
            }

            @Override
            public String toString() {
                return type;
            }
        };
    }

}
