/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.graphql.scalar;

import graphql.schema.*;

/**
 * @author 应卓
 * @since 1.10.16
 */
public final class VoidScalarType {

    private static final Coercing<Object, Object> VOID_COERCING = new Coercing<Object, Object>() {
        @Override
        public Object serialize(Object dataFetcherResult) throws CoercingSerializeException {
            return Void.INSTANCE;
        }

        @Override
        public Object parseValue(Object input) throws CoercingParseValueException {
            return Void.INSTANCE;
        }

        @Override
        public Object parseLiteral(Object input) throws CoercingParseLiteralException {
            return Void.INSTANCE;
        }
    };

    public static final GraphQLScalarType INSTANCE = GraphQLScalarType.newScalar()
            .name("Void")
            .description("void")
            .coercing(VOID_COERCING)
            .build();

}
