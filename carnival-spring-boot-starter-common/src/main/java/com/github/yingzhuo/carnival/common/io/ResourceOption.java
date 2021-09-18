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
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.Serializable;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @author 应卓
 * @since 1.10.21
 */
public final class ResourceOption implements Serializable {

    private final List<Resource> allExistsResources;
    private final Resource firstExists;
    private final Resource firstFile;
    private final Resource firstDirectory;

    private ResourceOption(List<Resource> allExistsResources, Resource firstExists, Resource firstFile, Resource firstDirectory) {
        this.allExistsResources = allExistsResources;
        this.firstExists = firstExists;
        this.firstFile = firstFile;
        this.firstDirectory = firstDirectory;
    }

    public static Builder builder() {
        return new Builder(null);
    }

    public static Builder builder(ResourceLoader resourceLoader) {
        return new Builder(resourceLoader);
    }

    public static ResourceOption of(String... locations) {
        return builder().addLocation(locations).build();
    }

    public static ResourceOption fromCommaSeparatedString(String string) {
        return of(string.split(","));
    }

    public int size() {
        return allExistsResources.size();
    }

    public boolean isPresent() {
        return firstExists != null;
    }

    public boolean isAbsent() {
        return firstExists == null;
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

    public byte[] asBytes() {
        try {
            return StreamUtils.copyToByteArray(firstFile().get().getInputStream());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public byte[] asBytesQuietly() {
        try {
            return asBytes();
        } catch (Exception e) {
            return new byte[0];
        }
    }

    public Path asPath() {
        try {
            return firstFile().get().getFile().toPath();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public Path asPathQuietly() {
        try {
            return asPath();
        } catch (Exception e) {
            return null;
        }
    }

    public List<Resource> allExistsResource() {
        return allExistsResources;
    }

    // -----------------------------------------------------------------------------------------------------------------

    public final static class Builder {

        private final ResourceLoader resourceLoader;
        private final List<Resource> allResources = new LinkedList<>();

        private Builder(ResourceLoader resourceLoader) {
            this.resourceLoader = resourceLoader != null ? resourceLoader : new DefaultResourceLoader();
        }

        public Builder addLocation(String... locations) {
            if (locations != null) {
                Arrays.stream(locations).map(resourceLoader::getResource).forEach(this.allResources::add);
            }
            return this;
        }

        public Builder addLocation(Collection<String> locations) {
            if (locations != null && !locations.isEmpty()) {
                locations.stream().map(resourceLoader::getResource).forEach(this.allResources::add);
            }
            return this;
        }

        public Builder addResource(Resource... resources) {
            if (resources != null) {
                Collections.addAll(this.allResources, resources);
            }
            return this;
        }

        public Builder addResource(Collection<Resource> resources) {
            if (resources != null && !resources.isEmpty()) {
                this.allResources.addAll(resources);
            }
            return this;
        }

        public ResourceOption build() {
            Resource firstExists = this.allResources.stream().filter(Resource::exists).findFirst().orElse(null);
            Resource firstFile = this.allResources.stream().filter(this::isFile).findFirst().orElse(null);
            Resource firstDirectory = this.allResources.stream().filter(this::isDirectory).findFirst().orElse(null);

            return new ResourceOption(
                    Collections.unmodifiableList(allResources.stream().filter(Resource::exists).collect(Collectors.toList())),
                    firstExists,
                    firstFile,
                    firstDirectory
            );
        }

        private boolean isFile(Resource resource) {
            try {
                return resource.exists() && resource.getFile().isFile();
            } catch (IOException e) {
                return false;
            }
        }

        private boolean isDirectory(Resource resource) {
            try {
                return resource.exists() && resource.getFile().isDirectory();
            } catch (IOException e) {
                return false;
            }
        }
    }

}
