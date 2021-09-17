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

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.Serializable;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.function.Consumer;

/**
 * @author 应卓
 * @see ResourceOptionBuilder
 * @since 1.10.21
 */
public final class ResourceOption implements Serializable {

    private final List<Resource> allResources;
    private final Resource firstExists;
    private final String firstExistsLocation;
    private final Resource firstFile;
    private final String firstFileLocation;
    private final Resource firstDirectory;
    private final String firstDirectoryLocation;

    ResourceOption(List<Resource> allResources, Resource firstExists, String firstExistsLocation, Resource firstFile, String firstFileLocation, Resource firstDirectory, String firstDirectoryLocation) {
        this.allResources = allResources;
        this.firstExists = firstExists;
        this.firstExistsLocation = firstExistsLocation;
        this.firstFile = firstFile;
        this.firstFileLocation = firstFileLocation;
        this.firstDirectory = firstDirectory;
        this.firstDirectoryLocation = firstDirectoryLocation;
    }

    public static ResourceOptionBuilder builder() {
        return new ResourceOptionBuilder(null);
    }

    public static ResourceOptionBuilder builder(ResourceLoader resourceLoader) {
        return new ResourceOptionBuilder(resourceLoader);
    }

    public static ResourceOption of(String... locations) {
        return builder().add(locations).build();
    }

    public static ResourceOption fromCommaSeparatedString(String string) {
        return of(string.split(","));
    }

    public boolean isPresent() {
        return firstExists != null;
    }

    public boolean isAbsent() {
        return !isPresent();
    }

    public void ifPresent(Consumer<? super Resource> consumer) {
        Optional.ofNullable(this.firstExists).ifPresent(consumer);
    }

    public Optional<Resource> firstExists() {
        return Optional.ofNullable(firstExists);
    }

    public Optional<Resource> firstFile() {
        return Optional.ofNullable(firstFile);
    }

    public Optional<Resource> firstDirectory() {
        return Optional.ofNullable(firstDirectory);
    }

    public String asText() {
        return asText(StandardCharsets.UTF_8);
    }

    public String asText(Charset charset) {
        try {
            return StreamUtils.copyToString(firstFile().get().getInputStream(), charset);
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
                properties.loadFromXML(firstFile().get().getInputStream());
            } else {
                properties.load(firstFile().get().getInputStream());
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

    public List<Resource> getAllResources() {
        return allResources;
    }

    public String firstExistsLocation() {
        return firstExistsLocation;
    }

    public String firstFileLocation() {
        return firstFileLocation;
    }

    public String firstDirectoryLocation() {
        return firstDirectoryLocation;
    }

}
