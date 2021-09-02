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

import com.github.yingzhuo.carnival.spring.BeanFinder;
import graphql.GraphQLError;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.graphql.execution.DataFetcherExceptionResolver;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author 应卓
 * @since 1.10.14
 */
public class DelegatingExceptionResolver implements DataFetcherExceptionResolver, InitializingBean, ApplicationContextAware {

    private BeanFinder beanFinder;
    private List<ExceptionResolver> exceptionResolvers;

    @Override
    public Mono<List<GraphQLError>> resolveException(Throwable exception, DataFetchingEnvironment environment) {

        for (ExceptionResolver resolver : exceptionResolvers) {
            if (resolver.support(exception)) {
                final List<GraphQLError> errors = resolver.resolve(exception, environment);
                return Mono.fromCallable(() -> errors);
            }
        }

        return Mono.empty();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.beanFinder = new BeanFinder(applicationContext);
    }

    @Override
    public void afterPropertiesSet() {
        this.exceptionResolvers = beanFinder.getMultipleQuietly(ExceptionResolver.class);
    }

}
