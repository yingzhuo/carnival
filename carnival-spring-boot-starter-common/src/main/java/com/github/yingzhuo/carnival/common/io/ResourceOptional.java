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
            return new ResourceOptionalImpl(locations);
        }
    }

    public static ResourceOptional empty() {
        return new AbsentResourceOptional();
    }

    public default Optional<Resource> toOptional() {
        return isPresent() ? Optional.of(get()) : Optional.empty();
    }

    public Resource get();

    public String getLocation();

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
        return function.apply(get());
    }

    public default void ifPresent(Consumer<Resource> consumer) {
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

    public default ResourceText toResourceText() {
        if (isAbsent()) {
            throw new NoSuchElementException("ResourceOptional is absent");
        }
        return ResourceText.of(get());
    }

    public default ResourceProperties toResourceProperties() {
        if (isAbsent()) {
            throw new NoSuchElementException("ResourceOptional is absent");
        }
        return ResourceProperties.of(getLocation());
    }

    // -----------------------------------------------------------------------------------------------------------------

    static class AbsentResourceOptional implements ResourceOptional {
        @Override
        public Resource get() {
            throw new NoSuchElementException("ResourceOptional is absent");
        }

        @Override
        public String getLocation() {
            throw new NoSuchElementException("ResourceOptional is absent");
        }

        @Override
        public boolean isPresent() {
            return false;
        }

        @Override
        public void close() {
            // NOP
        }
    }

}
