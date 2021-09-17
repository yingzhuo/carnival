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

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author 应卓
 * @since 1.10.21
 */
public final class ResourceOptionBuilder {

    private final ResourceLoader resourceLoader;
    private final List<Resource> allResources = new LinkedList<>();
    private final List<String> allLocations = new LinkedList<>();

    private Resource firstExists;
    private String firstExistsLocation;
    private Resource firstFile;
    private String firstFileLocation;
    private Resource firstDirectory;
    private String firstDirectoryLocation;

    ResourceOptionBuilder(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader != null ? resourceLoader : new DefaultResourceLoader();
    }

    public ResourceOptionBuilder add(String... locations) {
        for (String location : locations) {
            this.allLocations.add(location);
            this.allResources.add(resourceLoader.getResource(location));
        }
        return this;
    }

    public ResourceOptionBuilder add(Resource... resources) {
        for (Resource resource : resources) {
            this.allLocations.add(null);
            this.allResources.add(resource);
        }
        return this;
    }

    public ResourceOption build() {
        for (int i = 0; i < allResources.size(); i++) {
            final Resource resource = allResources.get(i);
            if (resource.exists() && this.firstExists != null) {
                this.firstExists = resource;
                this.firstExistsLocation = this.allLocations.get(i);
            }

            if (isFile(resource) && this.firstFile == null) {
                this.firstFile = resource;
                this.firstFileLocation = this.allLocations.get(i);
            }

            if (isDirectory(resource) && this.firstDirectory == null) {
                this.firstDirectory = resource;
                this.firstDirectoryLocation = this.allLocations.get(i);
            }

            if (firstExists != null && firstFile != null && firstDirectory != null) {
                break;
            }
        }

        return new ResourceOption(
                Collections.unmodifiableList(allResources),
                this.firstExists,
                this.firstExistsLocation,
                this.firstFile,
                this.firstFileLocation,
                this.firstDirectory,
                this.firstDirectoryLocation
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
