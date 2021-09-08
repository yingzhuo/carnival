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
import org.springframework.core.OrderComparator;
import org.springframework.graphql.execution.DataFetcherExceptionResolver;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 应卓
 * @since 1.10.14
 */
public class DelegatingExceptionResolver implements DataFetcherExceptionResolver, InitializingBean, ApplicationContextAware {

    private final List<ExceptionResolver> exceptionResolvers = new ArrayList<>();
    private BeanFinder beanFinder;

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
        exceptionResolvers.addAll(beanFinder.getMultipleQuietly(ExceptionResolver.class));

        ExceptionResolverCollection collection = beanFinder.getPrimaryQuietly(ExceptionResolverCollection.class).orElse(null);
        if (collection != null) {
            exceptionResolvers.addAll(collection.getExceptionResolvers());
        }

        OrderComparator.sort(exceptionResolvers);
    }

}
