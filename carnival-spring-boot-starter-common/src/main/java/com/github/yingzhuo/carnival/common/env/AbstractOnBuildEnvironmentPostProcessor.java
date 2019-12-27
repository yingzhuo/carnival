/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.common.env;

import com.github.yingzhuo.carnival.common.io.ResourceOptional;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.env.PropertySourceLoader;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * @author 应卓
 * @since 1.3.7
 */
public abstract class AbstractOnBuildEnvironmentPostProcessor implements EnvironmentPostProcessor {

    private final String[] locations;
    private final PropertySourceLoader propertySourceLoader;

    public AbstractOnBuildEnvironmentPostProcessor(String[] locations, PropertySourceLoader propertySourceLoader) {
        this.locations = locations;
        this.propertySourceLoader = propertySourceLoader;
    }

    @Override
    public final void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        ResourceLoader resourceLoader = application.getResourceLoader();

        Resource resource = this.loadFirstResource(locations);

        if (resource != null) {
            try {
                List<PropertySource<?>> propertySources = propertySourceLoader.load(UUID.randomUUID().toString(), resource);
                propertySources.forEach(it -> environment.getPropertySources().addLast(it));
            } catch (IOException ignored) {
                // NOP
            }
        }
    }

    private Resource loadFirstResource(String[] locations) {
        return ResourceOptional.of(locations).orElse(null);
    }

}
