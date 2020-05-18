/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.common.util;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * @author 应卓
 * @since 1.6.8
 */
public final class StringArray implements Serializable, Supplier<String[]> {

    private String[] delegate;

    private StringArray(String[] delegate) {
        this.delegate = delegate != null ? delegate : new String[0];
    }

    public static StringArray fromCommaSpitedString(String string) {
        if (string == null) return new StringArray(null);
        return new StringArray(string.split(","));
    }

    public static StringArray of(String... array) {
        return new StringArray(array);
    }

    public static StringArray of(Collection<String> list) {
        if (list == null || list.isEmpty()) return new StringArray(null);
        return new StringArray(list.toArray(new String[0]));
    }

    public StringArray removeNullElement() {
        this.delegate = Stream.of(delegate)
                .filter(Objects::nonNull)
                .toArray(String[]::new);
        return this;
    }

    public StringArray removeEmptyElement() {
        this.delegate = Stream.of(delegate)
                .filter(s -> s == null || s.isEmpty())
                .toArray(String[]::new);
        return this;
    }

    public StringArray removeBlankElement() {
        this.delegate = Stream.of(delegate)
                .filter(s -> s == null || s.isEmpty() || "".equals(s.replaceAll("\\s", "")))
                .toArray(String[]::new);
        return this;
    }

    public StringArray trimForEach() {
        this.delegate = toStream()
                .map(s -> s != null ? s.trim() : null)
                .toArray(String[]::new);
        return this;
    }

    public StringArray removeBlankForEach() {
        this.delegate = toStream()
                .map(s -> s != null ? s.replaceAll(" ", "") : null)
                .toArray(String[]::new);
        return this;
    }

    public StringArray removeWhitespaceForEach() {
        this.delegate = toStream()
                .map(s -> s != null ? s.replaceAll("\\s", "") : null)
                .toArray(String[]::new);
        return this;
    }

    @Override
    public String[] get() {
        return delegate;
    }

    public Stream<String> toStream() {
        return Stream.of(delegate);
    }

    public List<String> toList() {
        return Arrays.asList(delegate);
    }

    public void forEach(Consumer<String> mapper) {
        if (!isEmpty()) {
            toStream().forEach(mapper);
        }
    }

    public StringArray map(Function<String, String> function) {
        return new StringArray(Stream.of(get()).map(function).toArray(String[]::new));
    }

    public int size() {
        return delegate.length;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StringArray that = (StringArray) o;
        return Arrays.equals(delegate, that.delegate);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(delegate);
    }

}
