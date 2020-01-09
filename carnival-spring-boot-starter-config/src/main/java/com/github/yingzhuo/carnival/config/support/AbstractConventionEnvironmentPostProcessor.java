/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.config.support;

import com.github.yingzhuo.carnival.config.loader.HoconPropertySourceLoader;
import com.github.yingzhuo.carnival.config.loader.TomlPropertySourceLoader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.env.PropertiesPropertySourceLoader;
import org.springframework.boot.env.PropertySourceLoader;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.CompositePropertySource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.Closeable;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author 应卓
 * @since 1.4.1
 */
public abstract class AbstractConventionEnvironmentPostProcessor implements EnvironmentPostProcessor {

    private final ResourceLoader resourceLoader = new DefaultResourceLoader();
    private final String[] locationsPrefix;
    private final String name;

    private final PropertySourceLoader hocon = new HoconPropertySourceLoader();
    private final PropertySourceLoader toml = new TomlPropertySourceLoader();
    private final PropertySourceLoader yaml = new YamlPropertySourceLoader();
    private final PropertySourceLoader prop = new PropertiesPropertySourceLoader();

    public AbstractConventionEnvironmentPostProcessor(String[] locationsPrefix) {
        this(locationsPrefix, null);
    }

    public AbstractConventionEnvironmentPostProcessor(String[] locationsPrefix, String name) {
        this.locationsPrefix = locationsPrefix;
        this.name = (null != name && !name.isEmpty()) ? name : UUID.randomUUID().toString();
    }

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {

        final String[] activeProfiles = environment.getActiveProfiles();
        final CompositePropertySource cps = new CompositePropertySource(name);

        // basic
        List<String> baseLocations = getBaseLocations();
        ResourceAndLocation resourceAndLocation = findFirstReadable(baseLocations);
        Optional.ofNullable(load(resourceAndLocation)).ifPresent(cps::addFirstPropertySource);

        // profile
        for (String profile : activeProfiles) {
            List<String> profileLocations = getProfileLocations(profile);
            resourceAndLocation = findFirstReadable(profileLocations);
            Optional.ofNullable(load(resourceAndLocation)).ifPresent(cps::addFirstPropertySource);
        }

        environment.getPropertySources()
                .addFirst(cps);
    }

    private PropertySource<?> load(ResourceAndLocation resourceAndLocation) {
        if (resourceAndLocation == null) {
            return null;
        }

        try (ResourceAndLocation rl = resourceAndLocation) {
            Resource resource = rl.resource;
            String location = rl.location;
            if (location.endsWith(".conf")) {
                return doLoad(hocon, resource, location);
            }
            if (location.endsWith(".yaml") || location.endsWith(".yml")) {
                return doLoad(yaml, resource, location);
            }
            if (location.endsWith(".toml")) {
                return doLoad(toml, resource, location);
            }
            if (location.endsWith(".properties") || location.endsWith(".xml")) {
                return doLoad(prop, resource, location);
            }
            return null;
        }
    }

    private PropertySource<?> doLoad(PropertySourceLoader loader, Resource resource, String location) {
        final String name = String.format(" [%s]", location);

        try {
            List<PropertySource<?>> list = loader.load(name, resource);

            if (list.size() == 1) {
                return list.get(0);
            } else if (list.size() == 0) {
                return null;
            } else {
                throw new IllegalStateException("multiple document is NOT supported yet.");
            }

        } catch (IOException e) {
            return null;
        }
    }

    private ResourceAndLocation findFirstReadable(Iterable<String> locations) {
        for (String location : locations) {
            Resource resource = resourceLoader.getResource(location);
            if (resource.exists() && resource.isReadable()) {
                return new ResourceAndLocation(resource, location);
            }
        }
        return null;
    }

    private List<String> getBaseLocations() {
        List<String> list = new LinkedList<>();
        list.addAll(cross(new String[]{".conf"}));
        list.addAll(cross(new String[]{".toml"}));
        list.addAll(cross(new String[]{".yml", ".yaml"}));
        list.addAll(cross(new String[]{".properties", ".xml"}));
        return list;
    }

    private List<String> getProfileLocations(String profile) {
        List<String> list = new LinkedList<>();
        list.addAll(cross(profile, new String[]{".conf"}));
        list.addAll(cross(profile, new String[]{".toml"}));
        list.addAll(cross(profile, new String[]{".yml", ".yaml"}));
        list.addAll(cross(profile, new String[]{".properties", ".xml"}));
        return list;
    }

    private List<String> cross(String[] locationsSuffix) {
        List<String> list = new LinkedList<>();
        for (String prefix : locationsPrefix) {
            for (String suffix : locationsSuffix) {
                list.add(prefix + suffix);
            }
        }
        return list;
    }

    private List<String> cross(String profile, String[] locationsSuffix) {
        List<String> list = new LinkedList<>();

        for (String prefix : locationsPrefix) {
            for (String suffix : locationsSuffix) {
                list.add(prefix + "-" + profile + suffix);
            }
        }
        return list;
    }

    private static class ResourceAndLocation implements Closeable {
        private final Resource resource;
        private final String location;

        public ResourceAndLocation(Resource resource, String location) {
            this.resource = resource;
            this.location = location;
        }

        @Override
        public void close() {
            if (resource != null) {
                try {
                    resource.getInputStream().close();
                } catch (IOException ignore) {
                    // NOP
                }
            }
        }
    }
}
