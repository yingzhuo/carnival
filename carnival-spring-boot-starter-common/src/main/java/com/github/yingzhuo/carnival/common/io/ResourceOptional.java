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

import java.io.*;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author 应卓
 * @since 1.3.3
 */
public interface ResourceOptional extends Closeable {

    public static ResourceOptional of(String... locations) {
        if (locations == null || locations.length == 0) {
            return new AbsentResourceOptional();
        } else {
            return new SimpleResourceOptional(locations);
        }
    }

    public static ResourceOptional empty() {
        return new AbsentResourceOptional();
    }

    public default Optional<Resource> toOptional() {
        return Optional.ofNullable(get());
    }

    public Resource get();

    public default Resource orElse(Resource defaultResource) {
        return isPresent() ? get() : defaultResource;
    }

    public default Resource orElseGet(Supplier<Resource> supplier) {
        return isPresent() ? get() : supplier.get();
    }

    public default <X extends Throwable> Resource orElseThrow(Supplier<X> supplier) throws X {
        if (isPresent()) {
            return get();
        } else {
            throw supplier.get();
        }
    }

    public boolean isPresent();

    public default boolean isAbsent() {
        return !isPresent();
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

    public String getLocation();

    // -----------------------------------------------------------------------------------------------------------------

    public static final class AbsentResourceOptional implements ResourceOptional {
        @Override
        public Resource get() {
            throw new NoSuchElementException("ResourceOptional is absent");
        }

        @Override
        public boolean isPresent() {
            return false;
        }

        @Override
        public String getLocation() {
            throw new NoSuchElementException("ResourceOptional is absent");
        }

        @Override
        public void close() {
            // NOP
        }
    }

    // -----------------------------------------------------------------------------------------------------------------

    public static class SimpleResourceOptional implements ResourceOptional {

        private final static ResourceLoader RESOURCE_LOADER = new DefaultResourceLoader();
        private Resource resource;
        private String location;

        public SimpleResourceOptional(String... locations) {
            for (String location : locations) {
                Resource r = RESOURCE_LOADER.getResource(location);
                if (r.exists() && r.isReadable()) {
                    this.resource = r;
                    this.location = location;
                    return;
                }
            }

            this.resource = null;
        }

        @Override
        public Resource get() {
            if (isAbsent()) {
                throw new NoSuchElementException("ResourceOptional is absent");
            }
            return resource;
        }

        @Override
        public boolean isPresent() {
            return resource != null;
        }

        @Override
        public String getLocation() {
            if (isAbsent()) {
                throw new NoSuchElementException("ResourceOptional is absent");
            }
            return this.location;
        }

        @Override
        public void close() throws IOException {
            if (isPresent()) {
                get().getInputStream().close();
            }
        }
    }

}
