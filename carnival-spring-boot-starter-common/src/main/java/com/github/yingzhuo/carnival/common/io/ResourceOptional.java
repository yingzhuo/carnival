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

import com.google.common.base.Preconditions;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author 应卓
 * @since 1.3.3
 */
public interface ResourceOptional {

    public static ResourceOptional of(String... locations) {
        if (locations == null || locations.length == 0) {
            return new EmptyResourceOptional();
        } else {
            return new SimpleResourceOptional(locations);
        }
    }

    public static ResourceOptional empty() {
        return new EmptyResourceOptional();
    }

    public default Optional<Resource> toOptional() {
        return Optional.ofNullable(get());
    }

    public Resource get();

    public boolean isPresent();

    public default boolean isAbsent() {
        return !isPresent();
    }

    public default int size() {
        return isPresent() ? 1 : 0;
    }

    public default InputStream getInputStream() {
        if (isAbsent()) {
            throw new NoSuchElementException("ResourceOptional is absent");
        }

        try {
            return get().getInputStream();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public default File getFile() {
        if (isAbsent()) {
            throw new NoSuchElementException("ResourceOptional is absent");
        }

        try {
            return get().getFile();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public default String getFilename() {
        if (isAbsent()) {
            throw new NoSuchElementException("ResourceOptional is absent");
        }
        return get().getFilename();
    }

    public default <U> U map(Function<Resource, U> function) {
        Preconditions.checkArgument(function != null);
        return function.apply(get());
    }

    public default void ifPresent(Consumer<Resource> consumer) {
        Preconditions.checkArgument(consumer != null);
        consumer.accept(get());
    }

    public default void closeResource() {
        if (isPresent()) {
            try {
                getInputStream().close();
            } catch (IOException e) {
                // NOP
            }
        }
    }

    // -----------------------------------------------------------------------------------------------------------------

    public static final class EmptyResourceOptional implements ResourceOptional {
        @Override
        public Resource get() {
            throw new NoSuchElementException("ResourceOptional is absent");
        }

        @Override
        public boolean isPresent() {
            return false;
        }
    }

    // -----------------------------------------------------------------------------------------------------------------

    public static class SimpleResourceOptional implements ResourceOptional {

        private final static ResourceLoader RESOURCE_LOADER = new DefaultResourceLoader();
        private Resource resource;

        public SimpleResourceOptional(String... locations) {
            for (String location : locations) {
                Resource r = RESOURCE_LOADER.getResource(location);
                if (r.exists() && r.isReadable()) {
                    this.resource = r;
                    return;
                }
            }

            this.resource = null;
        }

        @Override
        public Resource get() {
            if (isPresent()) {
                throw new NoSuchElementException("ResourceOptional is absent");
            }
            return resource;
        }

        @Override
        public boolean isPresent() {
            return resource != null;
        }
    }
}