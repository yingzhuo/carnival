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
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.*;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.ClassUtils;

import java.util.*;

/**
 * @author 应卓
 * @since 1.6.17
 */
@Lazy(false)
@AutoConfigureAfter(FeignCoreAutoConfig.class)
public class FeignClientRegistrar implements ImportBeanDefinitionRegistrar, EnvironmentAware, ResourceLoaderAware {

    private Environment environment;
    private ResourceLoader resourceLoader;

    @Override
    public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry, BeanNameGenerator beanNameGenerator) {
        final Set<String> basePackages = getBasePackage(metadata);
        registerClients(basePackages, registry, beanNameGenerator);
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

    private void registerClients(Set<String> basePackages, BeanDefinitionRegistry registry, BeanNameGenerator beanNameGenerator) {

        ClassPathScanningCandidateComponentProvider scanner = createScanner();

        for (String basePackage : basePackages) {
            Set<BeanDefinition> candidateComponentBeanDefinitions = scanner.findCandidateComponents(basePackage);

            for (BeanDefinition beanDefinition : candidateComponentBeanDefinitions) {
                if (beanDefinition instanceof AnnotatedBeanDefinition) {
                    final AnnotatedBeanDefinition annotatedBeanDefinition = (AnnotatedBeanDefinition) beanDefinition;
                    final AnnotationMetadata annotationMetadata = annotatedBeanDefinition.getMetadata();
                    if (annotationMetadata.isInterface()) {
                        // 只有接口类型才有可能是Client
                        Map<String, Object> attrs = annotationMetadata.getAnnotationAttributes(FeignClient.class.getName());

                        // 注册单个Client
                        registerClient(attrs, annotatedBeanDefinition, registry, beanNameGenerator);
                    }
                }
            }
        }
    }

    private void registerClient(Map<String, Object> attrs, AnnotatedBeanDefinition beanDefinition, BeanDefinitionRegistry registry, BeanNameGenerator beanNameGenerator) {

        final String clientType = beanDefinition.getBeanClassName();

        final BeanDefinitionBuilder factoryBuilder =
                BeanDefinitionBuilder.genericBeanDefinition(FeignClientFactory.class);

        factoryBuilder.addPropertyValue("url", attrs.get("url"));
        factoryBuilder.addPropertyValue("urlSupplierType", attrs.get("urlSupplier"));
        factoryBuilder.addPropertyValue("clientType", clientType);

        factoryBuilder.setPrimary(true);
        factoryBuilder.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);

        AbstractBeanDefinition clientDefinition = factoryBuilder.getBeanDefinition();
        clientDefinition.setAttribute(FactoryBean.OBJECT_TYPE_ATTRIBUTE, beanDefinition.getBeanClassName());
        clientDefinition.setPrimary(true);

        BeanDefinitionHolder holder = new BeanDefinitionHolder(clientDefinition, clientType);
        BeanDefinitionReaderUtils.registerBeanDefinition(holder, registry);
    }

    private ClassPathScanningCandidateComponentProvider createScanner() {
        return new ClassPathScanningCandidateComponentProvider(false, environment) {
            {
                super.setResourceLoader(resourceLoader);
                super.addIncludeFilter(new AnnotationTypeFilter(FeignClient.class));
            }

            @Override
            protected boolean isCandidateComponent(
                    AnnotatedBeanDefinition beanDefinition) {
                boolean isCandidate = false;
                if (beanDefinition.getMetadata().isIndependent()) {
                    if (!beanDefinition.getMetadata().isAnnotation()) {
                        isCandidate = true;
                    }
                }
                return isCandidate;
            }
        };
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

}
