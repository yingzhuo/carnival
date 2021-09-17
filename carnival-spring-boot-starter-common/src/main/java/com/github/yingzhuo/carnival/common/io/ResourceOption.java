/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.common.io;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @author 应卓
 * @since 1.10.21
 */
public final class ResourceOption {

    private final List<String> locations;
    private final ResourceLoader resourceLoader;
    private final List<Resource> resources;
    private final Optional<Resource> firstExists;
    private final Optional<Resource> firstFile;
    private final Optional<Resource> firstDir;

    public ResourceOption(List<String> locations) {
        this(locations, new DefaultResourceLoader(ClassUtils.getDefaultClassLoader()));
    }

    public ResourceOption(List<String> locations, ResourceLoader resourceLoader) {
        Assert.notNull(locations, "locations is null");
        Assert.noNullElements(locations, "locations has null element(s)");
        Assert.notNull(resourceLoader, "resourceLoader is null");

        this.locations = locations;
        this.resourceLoader = resourceLoader;
        this.resources = this.locations.stream().map(this.resourceLoader::getResource).collect(Collectors.toList());
        this.firstExists = this.resources.stream().filter(Resource::exists).findFirst();
        this.firstFile = this.resources.stream().filter(Resource::exists).filter(Resource::isFile).findFirst();
        this.firstDir = this.resources.stream().filter(Resource::exists).filter(resource -> !resource.isFile()).findFirst();
    }

    public static ResourceOption of(String... locations) {
        return new ResourceOption(Arrays.asList(locations));
    }

    public static ResourceOption fromCommaSeparatedString(String string) {
        return of(string.split(","));
    }

    public boolean isPresent() {
        return firstExists.isPresent();
    }

    public boolean isAbsent() {
        return !isPresent();
    }

    public void ifPresent(Consumer<? super Resource> consumer) {
        this.firstExists.ifPresent(consumer);
    }

    public Optional<Resource> firstExists() {
        return this.firstExists;
    }

    public Optional<Resource> firstFile() {
        return this.firstFile;
    }

    public Optional<Resource> firstDirectory() {
        return this.firstDir;
    }

    public String asText() {
        return asText(StandardCharsets.UTF_8);
    }

    public String asText(Charset charset) {
        try {
            return StreamUtils.copyToString(this.firstFile.get().getInputStream(), charset);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public String asTextQuietly() {
        return asTextQuietly(StandardCharsets.UTF_8);
    }

    public String asTextQuietly(Charset charset) {
        try {
            return asText(charset);
        } catch (Exception e) {
            return null;
        }
    }

    public Properties asProperties() {
        return asProperties(false);
    }

    public Properties asProperties(boolean xmlFormat) {
        try {
            Properties properties = new Properties();
            if (xmlFormat) {
                properties.loadFromXML(this.firstFile.get().getInputStream());
            } else {
                properties.load(this.firstFile.get().getInputStream());
            }
            return properties;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public Properties asPropertiesQuietly() {
        return asPropertiesQuietly(false);
    }

    public Properties asPropertiesQuietly(boolean xmlFormat) {
        try {
            return asProperties(xmlFormat);
        } catch (Exception e) {
            return null;
        }
    }

}
