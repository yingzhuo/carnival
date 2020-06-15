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
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.env.Environment;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;

/**
 * @author 应卓
 * @since 1.6.18
 */
public final class ScanningUtils {

    public static final Filter FILTER_IS_INTERFACE = it -> it.getMetadata().isInterface();
    public static final Filter FILTER_IS_NOT_INTERFACE = it -> !it.getMetadata().isInterface();
    public static final Filter FILTER_IS_ABSTRACT = it -> it.getMetadata().isAbstract();
    public static final Filter FILTER_IS_NOT_ABSTRACT = it -> !it.getMetadata().isAbstract();

    // -----------------------------------------------------------------------------------------------------------------
    public static final Filter FILTER_IS_FINAL_FILTER = it -> it.getMetadata().isFinal();
    public static final Filter FILTER_IS_NOT_FINAL = it -> !it.getMetadata().isFinal();

    private ScanningUtils() {
    }

    public static Set<AnnotatedBeanDefinition> scan(
            Class<? extends Annotation> annotationType,
            Set<String> basePackages) {
        return scan(annotationType, new StandardEnvironment(), new DefaultResourceLoader(), basePackages);
    }

    public static Set<AnnotatedBeanDefinition> scan(
            Class<? extends Annotation> annotationType,
            Environment environment,
            Set<String> basePackages) {
        return scan(annotationType, environment, new DefaultResourceLoader(), basePackages);
    }

    public static Set<AnnotatedBeanDefinition> scan(
            Class<? extends Annotation> annotationType,
            Environment environment,
            ResourceLoader resourceLoader,
            Set<String> basePackages) {

        Objects.requireNonNull(annotationType);
        Objects.requireNonNull(environment);
        Objects.requireNonNull(resourceLoader);
        Objects.requireNonNull(basePackages);

        if (basePackages.isEmpty()) {
            return Collections.emptySet();
        }

        final Set<AnnotatedBeanDefinition> set = new HashSet<>();
        Scanner scanner = new Scanner(annotationType, environment, resourceLoader);

        for (String basePackage : basePackages) {
            Set<BeanDefinition> found = scanner.findCandidateComponents(basePackage);
            for (BeanDefinition candidate : found) {
                if (candidate instanceof AnnotatedBeanDefinition) {
                    set.add((AnnotatedBeanDefinition) candidate);
                }
            }
        }

        return set;
    }

    @FunctionalInterface
    public static interface Filter extends Predicate<AnnotatedBeanDefinition> {
    }

    // -----------------------------------------------------------------------------------------------------------------

    private static class Scanner extends ClassPathScanningCandidateComponentProvider {
        private Scanner(Class<? extends Annotation> annotationType, Environment environment, ResourceLoader resourceLoader) {
            super(false, environment);
            super.setResourceLoader(resourceLoader);
            super.addIncludeFilter(new AnnotationTypeFilter(annotationType));
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
