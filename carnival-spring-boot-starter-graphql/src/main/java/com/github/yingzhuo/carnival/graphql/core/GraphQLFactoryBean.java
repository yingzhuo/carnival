/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.graphql.core;

import com.github.yingzhuo.carnival.graphql.annotation.Action;
import com.github.yingzhuo.carnival.graphql.annotation.Argument;
import com.github.yingzhuo.carnival.graphql.annotation.Query;
import com.github.yingzhuo.carnival.graphql.schema.SchemaText;
import com.github.yingzhuo.carnival.spring.RequestMappingUtils;
import graphql.GraphQL;
import graphql.schema.DataFetcher;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.*;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 应卓
 * @since 1.10.14
 */
public class GraphQLFactoryBean implements FactoryBean<GraphQL>, BeanPostProcessor {

    private final SchemaText schemaText;
    private final Map<String, DataFetcher<?>> dataFetcherMap = new HashMap<>();

    public GraphQLFactoryBean(SchemaText schemaText) {
        this.schemaText = schemaText;
    }

    @Override
    public GraphQL getObject() {
        return GraphQL.newGraphQL(this.buildSchema(this.schemaText.getText()))
                .build();
    }

    @Override
    public Class<?> getObjectType() {
        return GraphQL.class;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        for (Entry entry : parse(bean)) {
            this.dataFetcherMap.put(entry.key, entry.value);
        }
        return bean;
    }

    private List<Entry> parse(Object bean) {
        final List<Entry> list = new ArrayList<>();
        final Class<?> beanType = bean.getClass();
        final Action action = AnnotationUtils.findAnnotation(beanType, Action.class);

        if (action == null) {
            return list;
        }

        final Method[] methods = ReflectionUtils.getAllDeclaredMethods(beanType);
        for (Method method : methods) {
            final Query query = AnnotationUtils.findAnnotation(method, Query.class);
            if (query == null) {
                continue;
            }
            list.add(createEntry(bean, query, method));
        }

        return list;
    }

    private Entry createEntry(final Object bean, final Query query, final Method method) {
        final String name = getQueryName(query, method);

        final List<Argument> arguments = new ArrayList<>(method.getParameterCount());
        for (int i = 0; i < method.getParameterCount(); i++) {
            final MethodParameter mp = new MethodParameter(method, i);
            Argument argument = mp.getParameterAnnotation(Argument.class);
            arguments.add(argument);
        }

        final DataFetcher<?> dataFetcher = new ReflectionDataFetcher(arguments, bean, method, RequestMappingUtils.getHandlerMethodArgumentResolvers());
        return Entry.newInstance(name, dataFetcher);
    }

    private String getQueryName(Query query, Method method) {
        String name = query.value();
        if (StringUtils.hasText(name)) {
            return name;
        }
        return method.getName();
    }

    protected final GraphQLSchema buildSchema(String sdl) {
        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(sdl);
        RuntimeWiring runtimeWiring = buildWiring();
        SchemaGenerator schemaGenerator = new SchemaGenerator();
        return schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);
    }

    private RuntimeWiring buildWiring() {
        final RuntimeWiring.Builder builder = RuntimeWiring.newRuntimeWiring();

        for (String name : this.dataFetcherMap.keySet()) {
            final DataFetcher<?> dataFetcher = this.dataFetcherMap.get(name);
            builder.type(
                    TypeRuntimeWiring
                            .newTypeWiring("Query")
                            .dataFetcher(name, dataFetcher)
            );
        }

        return builder.build();
    }

    private static final class Entry {
        private String key;
        private DataFetcher<?> value;

        private static Entry newInstance(String key, DataFetcher<?> value) {
            final Entry e = new Entry();
            e.key = key;
            e.value = value;
            return e;
        }
    }

}
