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
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.core.env.Environment;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.util.Assert;

import java.util.*;

/**
 * @author 应卓
 * @since 1.6.20
 */
public final class ClassPathScannerBuilder {

    private Environment environment = new StandardEnvironment();
    private ResourceLoader resourceLoader = new DefaultResourceLoader();
    private final List<TypeFilter> typeFilters = new LinkedList<>();

    ClassPathScannerBuilder() {
    }

    public final ClassPathScannerBuilder typeFilters(TypeFilter... typeFilters) {
        Collections.addAll(this.typeFilters, typeFilters);
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

    public ClassPathScanner builder() {
        if (typeFilters.isEmpty()) {
            return new ClassPathScannerEmpty();
        } else {
            final ClassPathScannerImpl impl = new ClassPathScannerImpl();
            impl.setTypeFilters(typeFilters);
            impl.setEnvironment(environment);
            impl.setResourceLoader(resourceLoader);
            return impl;
        }
    }

    private static final class ClassPathScannerEmpty implements ClassPathScanner {
        @Override
        public Set<BeanDefinition> scan(Iterable<String> basePackages) {
            return Collections.emptySet();
        }
    }

    private static final class ClassPathScannerImpl implements ClassPathScanner {
        private final ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider();

        @Override
        public Set<BeanDefinition> scan(Iterable<String> basePackages) {
            final Set<BeanDefinition> set = new HashSet<>();

            for (String basePackage : basePackages) {
                set.addAll(provider.findCandidateComponents(basePackage));
            }

            return Collections.unmodifiableSet(set);
        }

        public void setResourceLoader(ResourceLoader resourceLoader) {
            provider.setResourceLoader(resourceLoader);
        }

        public void setEnvironment(Environment environment) {
            provider.setEnvironment(environment);
        }

        public void setTypeFilters(List<TypeFilter> typeFilters) {
            for (TypeFilter typeFilter : typeFilters) {
                provider.addIncludeFilter(typeFilter);
            }
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
