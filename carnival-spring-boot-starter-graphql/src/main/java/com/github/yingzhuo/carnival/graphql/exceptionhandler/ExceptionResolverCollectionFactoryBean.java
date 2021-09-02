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

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author 应卓
 * @since 1.10.14
 */
public class ExceptionResolverCollectionFactoryBean implements FactoryBean<ExceptionResolverCollection>, InitializingBean {

    protected final List<ExceptionResolver> resolvers = new ArrayList<>();

    public ExceptionResolverCollectionFactoryBean() {
    }

    public ExceptionResolverCollectionFactoryBean add(ExceptionResolver... resolvers) {
        this.resolvers.addAll(Arrays.asList(resolvers));
        return this;
    }

    @Override
    public ExceptionResolverCollection getObject() {
        return () -> resolvers;
    }

    @Override
    public final Class<?> getObjectType() {
        return ExceptionResolverCollection.class;
    }

    @Override
    public void afterPropertiesSet() {
        if (resolvers.isEmpty()) {
            resolvers.add(NullExceptionResolver.INSTANCE);
        }
    }

}
