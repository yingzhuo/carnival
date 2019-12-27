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

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * @author 应卓
 * @since 1.3.7
 */
public abstract class AbstractOnBuildEnvironmentPostProcessor implements EnvironmentPostProcessor {

    private final String[] locations;
    private final String[] exts;
    private final PropertySourceLoader propertySourceLoader;

    public AbstractOnBuildEnvironmentPostProcessor(String[] locations, String[] exts, PropertySourceLoader propertySourceLoader) {
        this.locations = locations;
        this.exts = exts;
        this.propertySourceLoader = propertySourceLoader;
    }

    @Override
    public final void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {

        final String name = UUID.randomUUID().toString().replaceAll("-", "");
        final List<PropertySource<?>> pss = new LinkedList<>();

        final Resource baseResource = this.loadFirstResource(cross(locations, exts));
        if (baseResource != null) {
            try {
                List<PropertySource<?>> propertySources = propertySourceLoader.load(name, baseResource);
                pss.addAll(propertySources);
            } catch (IOException ignored) {
                // NOP
            }
        }

        for (String profile : environment.getActiveProfiles()) {
            final Resource profileResource = this.loadFirstResource(cross(locations, profile, exts));

            if (profileResource != null) {
                try {
                    List<PropertySource<?>> propertySources = propertySourceLoader.load(name, profileResource);
                    pss.addAll(propertySources);
                } catch (IOException ignored) {
                    // NOP
                }
            }
        }

        pss.forEach(ps -> environment.getPropertySources().addLast(ps));
    }

    private Resource loadFirstResource(String[] locations) {
        return ResourceOptional.of(locations).orElse(null);
    }

    private String[] cross(String[] locations, String[] exts) {
        final List<String> list = new ArrayList<>();
        for (String location : locations) {
            for (String ext : exts) {
                list.add(location + ext);
            }
        }
        return list.toArray(new String[]{});
    }

    private String[] cross(String[] locations, String profile, String[] exts) {
        final List<String> list = new ArrayList<>();
        for (String location : locations) {
            for (String ext : exts) {
                list.add(location + "-" + profile + ext);
            }
        }
        return list.toArray(new String[]{});
    }

}
