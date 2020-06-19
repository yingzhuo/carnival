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

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

/**
 * @author 应卓
 * @since 1.6.20
 */
public interface ClassPathScanner {

    /**
     * 过滤器
     */
    @FunctionalInterface
    public static interface Filter extends Predicate<AnnotatedBeanDefinition> {
    }

    public static final Filter FILTER_IS_INTERFACE = it -> it.getMetadata().isInterface();
    public static final Filter FILTER_IS_NOT_INTERFACE = it -> !it.getMetadata().isInterface();
    public static final Filter FILTER_IS_ABSTRACT = it -> it.getMetadata().isAbstract();
    public static final Filter FILTER_IS_NOT_ABSTRACT = it -> !it.getMetadata().isAbstract();
    public static final Filter FILTER_IS_FINAL_FILTER = it -> it.getMetadata().isFinal();
    public static final Filter FILTER_IS_NOT_FINAL = it -> !it.getMetadata().isFinal();

    public static final class CompositeFilter implements ClassPathScanner.Filter {

        public static Filter of(Filter... filters) {
            return new CompositeFilter(Arrays.asList(filters));
        }

        private final List<Filter> filters;

        public CompositeFilter(List<ClassPathScanner.Filter> filters) {
            this.filters = filters;
        }

        @Override
        public boolean test(AnnotatedBeanDefinition annotatedBeanDefinition) {
            for (ClassPathScanner.Filter filter : filters) {
                if (filter.test(annotatedBeanDefinition)) {
                    return false;
                }
            }
            return false;
        }
    }

    /**
     * 创建扫描器
     */
    public static ClassPathScannerBuilder builder() {
        return new ClassPathScannerBuilder();
    }

    /**
     * 扫描
     *
     * @param basePackages 扫描起点
     * @return 扫描结果
     */
    public Set<AnnotatedBeanDefinition> scan(Iterable<String> basePackages);

}
