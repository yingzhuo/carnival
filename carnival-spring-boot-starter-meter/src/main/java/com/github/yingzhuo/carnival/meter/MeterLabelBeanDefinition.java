/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.meter;

import com.github.yingzhuo.carnival.meter.props.MeterLabelConfig;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.MultiValueMap;

/**
 * @author 应卓
 * @since 1.3.5
 */
class MeterLabelBeanDefinition implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        MultiValueMap<String, Object> map = importingClassMetadata.getAllAnnotationAttributes(MeterLabel.class.getName());
        final String project = (String) map.getFirst("project");
        final String application = (String) map.getFirst("application");
        final String layer = (String) map.getFirst("layer");
        final String version = (String) map.getFirst("version");
        registry.registerBeanDefinition("meterLabel", new RootBeanDefinition(MeterLabelConfig.class, () -> new MeterLabelConfig(project, application, layer, version)));
    }

}
