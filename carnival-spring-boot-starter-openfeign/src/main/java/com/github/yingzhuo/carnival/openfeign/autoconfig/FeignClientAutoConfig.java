/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.openfeign.autoconfig;

import com.github.yingzhuo.carnival.openfeign.EnableFeignClients;
import com.github.yingzhuo.carnival.openfeign.FeignClient;
import com.github.yingzhuo.carnival.scanning.ClassPathScanner;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.ClassUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 应卓
 * @since 1.6.17
 */
@AutoConfigureAfter(FeignBeanAutoConfig.class)
public class FeignClientAutoConfig implements ImportBeanDefinitionRegistrar, EnvironmentAware, ResourceLoaderAware {

    private Environment environment;
    private ResourceLoader resourceLoader;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
        final Set<String> basePackages = getBasePackage(metadata);
        registerClients(basePackages, registry);
    }

    private Set<String> getBasePackage(AnnotationMetadata metadata) {
        final Map<String, Object> attrs = metadata.getAnnotationAttributes(EnableFeignClients.class.getName());
        final Set<String> set = new HashSet<>();
        Collections.addAll(set, (String[]) attrs.get("basePackages"));
        Collections.addAll(set, (String[]) attrs.get("value"));
        Collections.addAll(set, Arrays.stream((Class<?>[]) attrs.get("basePackageClasses")).map(ClassUtils::getPackageName).toArray(String[]::new));
        if (set.isEmpty()) {
            set.add(ClassUtils.getPackageName(metadata.getClassName()));
        }
        return set;
    }

    private void registerClients(Set<String> basePackages, BeanDefinitionRegistry registry) {

        final Set<AnnotatedBeanDefinition> scanned =
                ClassPathScanner.builder()
                        .typeFilters(new AnnotationTypeFilter(FeignClient.class))
                        .environment(environment)
                        .resourceLoader(resourceLoader)
                        .builder()
                        .scan(basePackages)
                        .stream()
                        .filter(it -> it instanceof AnnotatedBeanDefinition)
                        .map(it -> (AnnotatedBeanDefinition) it)
                        .collect(Collectors.toSet());

        for (AnnotatedBeanDefinition annotatedBeanDefinition : scanned) {
            AnnotationMetadata metadata = annotatedBeanDefinition.getMetadata();
            Map<String, Object> attrs = metadata.getAnnotationAttributes(FeignClient.class.getName());
            registerClient(attrs, annotatedBeanDefinition, registry);
        }
    }

    private void registerClient(Map<String, Object> attrs, AnnotatedBeanDefinition beanDefinition, BeanDefinitionRegistry registry) {

        final String clientType = beanDefinition.getBeanClassName();

        final BeanDefinitionBuilder factoryBuilder =
                BeanDefinitionBuilder.genericBeanDefinition(FeignClientFactory.class);

        final String url = resolveUrl(attrs);

        factoryBuilder.addPropertyValue("clientType", clientType);
        factoryBuilder.addPropertyValue("url", url);
        factoryBuilder.addPropertyValue("urlSupplierType", attrs.get("urlSupplier"));
        factoryBuilder.addPropertyValue("configurationType", attrs.get("configuration"));

        factoryBuilder.setPrimary(true);
        factoryBuilder.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);

        AbstractBeanDefinition clientDefinition = factoryBuilder.getBeanDefinition();
        clientDefinition.setAttribute(FactoryBean.OBJECT_TYPE_ATTRIBUTE, beanDefinition.getBeanClassName());
        clientDefinition.setPrimary(true);

        BeanDefinitionHolder holder = new BeanDefinitionHolder(clientDefinition, clientType);
        BeanDefinitionReaderUtils.registerBeanDefinition(holder, registry);
    }

    private String resolveUrl(Map<String, Object> attrs) {
        final String template = (String) attrs.get("url");

        if (template == null || "".equals(template)) {
            return "";
        }

        try {
            return environment.resolveRequiredPlaceholders(template);
        } catch (IllegalArgumentException e) {
            return template;
        }
    }

}
