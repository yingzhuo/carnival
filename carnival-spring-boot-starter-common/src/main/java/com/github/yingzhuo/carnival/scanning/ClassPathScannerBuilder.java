/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.scanning;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.core.env.Environment;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.Assert;

import java.lang.annotation.Annotation;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 应卓
 * @since 1.6.20
 */
public final class ClassPathScannerBuilder {

    private List<ClassPathScanner.Filter> filters = new ArrayList<>();
    private Environment environment = new StandardEnvironment();
    private ResourceLoader resourceLoader = new DefaultResourceLoader();
    private List<Class<? extends Annotation>> annotationTypes = new ArrayList<>();

    ClassPathScannerBuilder() {
    }

    @SafeVarargs
    public final ClassPathScannerBuilder annotation(Class<? extends Annotation>... annotationTypes) {
        Assert.notNull(annotationTypes, "annotationType is null");
        Collections.addAll(this.annotationTypes, annotationTypes);
        return this;
    }

    public ClassPathScannerBuilder environment(Environment environment) {
        Assert.notNull(environment, "environment is null");
        this.environment = environment;
        return this;
    }

    public ClassPathScannerBuilder resourceLoader(ResourceLoader resourceLoader) {
        Assert.notNull(resourceLoader, "resourceLoader is null");
        this.resourceLoader = resourceLoader;
        return this;
    }

    public ClassPathScannerBuilder filters(ClassPathScanner.Filter... filters) {
        this.filters = Arrays.asList(filters);
        return this;
    }

    public ClassPathScanner builder() {
        Assert.notEmpty(annotationTypes, "annotationType not set");

        final ClassPathScannerImpl impl = new ClassPathScannerImpl();
        impl.setAnnotations(annotationTypes);
        impl.setEnvironment(environment);
        impl.setResourceLoader(resourceLoader);
        impl.setFilters(filters);
        return impl;
    }

    private static final class ClassPathScannerImpl implements ClassPathScanner {
        private ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider();
        private List<ClassPathScanner.Filter> filters;

        @Override
        public Set<AnnotatedBeanDefinition> scan(Iterable<String> basePackages) {
            final Set<AnnotatedBeanDefinition> set = new HashSet<>();

            for (String basePackage : basePackages) {
                set.addAll(
                        provider.findCandidateComponents(basePackage)
                                .stream()
                                .filter(it -> it instanceof AnnotatedBeanDefinition)
                                .map(it -> (AnnotatedBeanDefinition) it)
                                .collect(Collectors.toSet())
                );
            }

            if (filters == null || filters.isEmpty()) {
                return Collections.unmodifiableSet(set);
            }

            if (filters.size() == 1) {
                return Collections.unmodifiableSet(
                        set.stream()
                                .filter(filters.get(0))
                                .collect(Collectors.toSet())
                );
            } else {
                return Collections.unmodifiableSet(
                        set.stream()
                                .filter(new CompositeFilter(filters))
                                .collect(Collectors.toSet())
                );
            }
        }

        public void setResourceLoader(ResourceLoader resourceLoader) {
            provider.setResourceLoader(resourceLoader);
        }

        public void setEnvironment(Environment environment) {
            provider.setEnvironment(environment);
        }

        public void setAnnotations(List<Class<? extends Annotation>> annotationTypes) {
            annotationTypes.forEach(type -> provider.addIncludeFilter(new AnnotationTypeFilter(type)));
        }

        public void setFilters(List<Filter> filters) {
            this.filters = filters;
        }
    }

    private static final class ClassPathScanningCandidateComponentProvider
            extends org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider {

        private ClassPathScanningCandidateComponentProvider() {
            super(false);
        }

        @Override
        protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
            boolean isCandidate = false;
            if (beanDefinition.getMetadata().isIndependent()) {
                if (!beanDefinition.getMetadata().isAnnotation()) {
                    isCandidate = true;
                }
            }
            return isCandidate;
        }
    }

}
