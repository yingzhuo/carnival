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
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author 应卓
 * @since 1.6.18
 */
public final class ScannerUtils {

    private ScannerUtils() {
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

        if (basePackages == null || basePackages.isEmpty()) {
            return Collections.emptySet();
        }

        Set<AnnotatedBeanDefinition> set = new HashSet<>();

        Scanner scanner = new Scanner(annotationType, environment, resourceLoader);

        for (String basePackage : basePackages) {
            Set<BeanDefinition> found = scanner.findCandidateComponents(basePackage);
            for (BeanDefinition candidate : found) {
                if (candidate instanceof AnnotatedBeanDefinition) {
                    set.add((AnnotatedBeanDefinition) candidate);
                }
            }
        }

        return Collections.unmodifiableSet(set);
    }


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
