/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.openfeign.target;

import feign.Target;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * @param <T> type of client
 * @author 应卓
 * @since 1.6.16
 */
public final class LazyTarget<T> extends Target.HardCodedTarget<T> {

    public static <T> Target<T> of(Class<T> type, UrlSupplier urlSupplier) {
        return new LazyTarget<>(type, urlSupplier);
    }

    private final Supplier<String> urlSupplier;

    public LazyTarget(Class<T> type, UrlSupplier urlSupplier) {
        super(type, "url");
        this.urlSupplier = Objects.requireNonNull(urlSupplier);
    }

    @Override
    public String url() {
        return urlSupplier.get();
    }

    @FunctionalInterface
    public static interface UrlSupplier extends Supplier<String> {
    }

}
