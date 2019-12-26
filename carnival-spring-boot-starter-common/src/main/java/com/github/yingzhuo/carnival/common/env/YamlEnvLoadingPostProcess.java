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
        ResourceOptional op = ResourceOptional.of(
                "file:config/custom.yml",
                "file:config/custom.yaml",
                "classpath:/config/custom.yml",
                "classpath:/config/custom.yaml",
                "classpath:/custom.yml",
                "classpath:/custom.yaml",
                "classpath:/META-INF/custom.yml",
                "classpath:/META-INF/custom.yaml"
        );

        if (op.isAbsent()) {
            return;
        }

        try {
            List<PropertySource<?>> propertySources = loader.load("custom-yaml", op.get());
            environment.getPropertySources().addLast(propertySources.get(0));
        } catch (IOException ignored) {

        }
    }

}
