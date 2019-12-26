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
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;

import java.io.IOException;
import java.util.List;

/**
 * @author 应卓
 * @since 1.3.7
 */
public class YamlEnvLoadingPostProcess implements EnvironmentPostProcessor {

    private final YamlPropertySourceLoader loader = new YamlPropertySourceLoader();

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        final ResourceOptional op = ResourceOptional.of(
                "file:config/property-source.yml",
                "file:config/property-source.yaml",
                "file:property-source.yml",
                "file:property-source.yaml",
                "classpath:/config/property-source.yml",
                "classpath:/config/property-source.yaml",
                "classpath:/property-source.yml",
                "classpath:/property-source.yaml",
                "classpath:/META-INF/property-source.yml",
                "classpath:/META-INF/property-source.yaml"
        );

        if (op.isAbsent()) {
            return;
        }

        try {
            List<PropertySource<?>> propertySources = loader.load("yaml-property-source", op.get());
            environment.getPropertySources().addLast(propertySources.get(0));
        } catch (IOException ignored) {
            // NOP
        }
    }

}
