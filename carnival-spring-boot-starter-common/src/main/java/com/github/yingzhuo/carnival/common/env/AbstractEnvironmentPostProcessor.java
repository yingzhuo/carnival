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
import lombok.val;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.env.PropertySourceLoader;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Stack;
import java.util.UUID;

/**
 * @author 应卓
 * @since 1.4.0
 */
public abstract class AbstractEnvironmentPostProcessor implements EnvironmentPostProcessor {

    private final String[] locationsPrefix;
    private final String[] locationsSuffix;
    private final String name;
    private final Stack<PropertySource<?>> stack = new Stack<>();
    private final PropertySourceLoader propertySourceLoader;

    public AbstractEnvironmentPostProcessor(String[] locationsPrefix, String[] locationsSuffix, String name, PropertySourceLoader propertySourceLoader) {
        this.locationsPrefix = locationsPrefix;
        this.locationsSuffix = locationsSuffix;
        this.name = name;
        this.propertySourceLoader = propertySourceLoader;
    }

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        val defaultResource = ResourceOptional.of(cross()).orElse(null);
        push(defaultResource);

        for (val profile : environment.getActiveProfiles()) {
            val profileResource = ResourceOptional.of(cross(profile)).orElse(null);
            push(profileResource);
        }

        while (!stack.isEmpty()) {
            environment.getPropertySources().addLast(stack.pop());
        }
    }

    private void push(Resource resource) {
        if (resource == null) return;

        try {
            val list = propertySourceLoader.load(name + "-" + UUID.randomUUID(), resource);

            switch (list.size()) {
                case 0:
                    return;
                case 1:
                    stack.push(list.get(0));
                    return;
                default:
                    throw new IllegalStateException("multiple document is NOT supported.");
            }

        } catch (IOException ignore) {
            // NOP
        }
    }

    private String[] cross() {
        val list = new LinkedList<String>();
        for (val prefix : locationsPrefix) {
            for (val suffix : locationsSuffix) {
                list.add(prefix + suffix);
            }
        }
        return list.toArray(new String[0]);
    }

    private String[] cross(String profile) {
        val list = new LinkedList<String>();

        for (val prefix : locationsPrefix) {
            for (val suffix : locationsSuffix) {
                list.add(prefix + "-" + profile + suffix);
            }
        }

        return list.toArray(new String[0]);
    }

}
