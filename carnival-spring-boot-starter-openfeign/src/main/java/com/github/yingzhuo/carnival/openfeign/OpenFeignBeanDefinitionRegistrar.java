/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.openfeign;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.ClassUtils;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author 应卓
 * @since 1.6.17
 */
@Slf4j
class OpenFeignBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar, EnvironmentAware, ResourceLoaderAware, ApplicationContextAware {

    private ApplicationContext applicationContext;
    private Environment environment;
    private ResourceLoader resourceLoader;

    @Override
    public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry, BeanNameGenerator beanNameGenerator) {
        final Set<String> basePackages = getBasePackage(metadata);
        registerClients(basePackages, registry, beanNameGenerator);
    }

    private Set<String> getBasePackage(AnnotationMetadata metadata) {
        final Map<String, Object> attrs = metadata.getAnnotationAttributes(EnableOpenFeignClients.class.getName());

        final Set<String> set = new HashSet<>();
        Collections.addAll(set, (String[]) attrs.get("basePackages"));

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
                        Map<String, Object> attrs = annotationMetadata.getAnnotationAttributes(OpenFeignClient.class.getName());

                        // 注册单个Client
                        registerClient(attrs, annotatedBeanDefinition, registry, beanNameGenerator);
                    }
                }
            }
        }
    }

    private void registerClient(Map<String, Object> attrs, AnnotatedBeanDefinition beanDefinition, BeanDefinitionRegistry registry, BeanNameGenerator beanNameGenerator) {
        // FIXME: 先打印一下再说

        final String clientType = beanDefinition.getBeanClassName();

        final BeanDefinitionBuilder factoryBuilder =
                BeanDefinitionBuilder.genericBeanDefinition(OpenFeignClientFactoryBean.class);

        factoryBuilder.addPropertyValue("url", attrs.get("url"));
        factoryBuilder.addPropertyValue("urlSupplierType", attrs.get("urlSupplier"));
        factoryBuilder.addPropertyValue("clientType", clientType);

        // primary bean ?
        boolean isPrimary = (Boolean) attrs.get("primary"); // 这里有默认值，不怕NPE
        factoryBuilder.setPrimary(isPrimary);
        factoryBuilder.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);

        AbstractBeanDefinition clientDefinition = factoryBuilder.getBeanDefinition();
        clientDefinition.setAttribute(FactoryBean.OBJECT_TYPE_ATTRIBUTE, beanDefinition.getBeanClassName());
        clientDefinition.setPrimary(isPrimary);

        BeanDefinitionHolder holder = new BeanDefinitionHolder(clientDefinition, clientType);
        BeanDefinitionReaderUtils.registerBeanDefinition(holder, registry);

        System.out.println("注册完成了");
    }


    private ClassPathScanningCandidateComponentProvider createScanner() {
        return new ClassPathScanningCandidateComponentProvider(false, environment) {
            {
                super.setResourceLoader(resourceLoader);
                super.addIncludeFilter(new AnnotationTypeFilter(OpenFeignClient.class));
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
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
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
