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

import static feign.Target.HardCodedTarget;

/**
 * @param <T> type of client
 * @author 应卓
 * @since 1.6.17
 */
public class CoreTarget<T> extends HardCodedTarget<T> implements Target<T> {

    private final UrlSupplier urlSupplier;

    public CoreTarget(Class<T> type, UrlSupplier urlSupplier) {
        super(Objects.requireNonNull(type), CoreTarget.class.getName());
        this.urlSupplier = Objects.requireNonNull(urlSupplier);
    }

    public static <T> Target<T> of(Class<T> type, UrlSupplier urlSupplier) {
        return new CoreTarget<>(type, urlSupplier);
    }

    @Override
    public String url() {
        String url = urlSupplier.get();
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "http://" + url;
        }
        return url;
    }

}
