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

import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author 应卓
 */
public final class ResourceOption {

    public static ResourceOption of(String... locations) {
        return new ResourceOption(locations);
    }

    private static final ResourceLoader RESOURCE_LOADER = new DefaultResourceLoader();

    private Optional<Resource> result = Optional.empty();

    private ResourceOption(String... locations) {
        for (String location : locations) {
            if (!result.isPresent()) {
                final Resource resource = RESOURCE_LOADER.getResource(location);
                if (resource != null && resource.exists()) {
                    result = Optional.of(resource);
                    return;
                }
            }
        }
    }

    public boolean isPresent() {
        return result.isPresent();
    }

    public boolean isAbsent() {
        return !isPresent();
    }

    public Resource get() {
        return result.get();
    }

    public <U> U map(Function<Resource, U> mapper) {
        Objects.requireNonNull(mapper);
        return mapper.apply(get());
    }

    public void ifPresent(Consumer<Resource> consumer) {
        if (isPresent()) {
            Objects.requireNonNull(consumer).accept(get());
        }
    }

}
