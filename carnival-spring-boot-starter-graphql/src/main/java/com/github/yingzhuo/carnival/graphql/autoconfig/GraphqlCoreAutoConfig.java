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

import com.github.yingzhuo.carnival.graphql.exceptionhandler.DelegatingExceptionResolver;
import com.github.yingzhuo.carnival.graphql.scalar.VoidScalarType;
import graphql.scalars.ExtendedScalars;
import graphql.schema.idl.RuntimeWiring;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

/**
 * @author 应卓
 * @since 1.10.14
 */
class GraphqlCoreAutoConfig implements RuntimeWiringConfigurer {

    @Override
    public void configure(RuntimeWiring.Builder builder) {
        builder.scalar(ExtendedScalars.Object)
                .scalar(ExtendedScalars.GraphQLBigDecimal)
                .scalar(ExtendedScalars.GraphQLBigInteger)
                .scalar(ExtendedScalars.GraphQLByte)
                .scalar(ExtendedScalars.GraphQLChar)
                .scalar(ExtendedScalars.GraphQLLong)
                .scalar(ExtendedScalars.GraphQLShort)
                .scalar(ExtendedScalars.Date)
                .scalar(ExtendedScalars.DateTime)
                .scalar(ExtendedScalars.Time)
                .scalar(ExtendedScalars.Json)
                .scalar(ExtendedScalars.Url)
                .scalar(ExtendedScalars.Locale)
                .scalar(VoidScalarType.INSTANCE);
    }

    @Bean
    @ConditionalOnMissingBean
    DelegatingExceptionResolver delegatingExceptionHandler() {
        return new DelegatingExceptionResolver();
    }

}
