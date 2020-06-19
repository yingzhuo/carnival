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

import org.springframework.core.env.Environment;

import java.util.Objects;

import static feign.Target.HardCodedTarget;

/**
 * @param <T> type of client
 * @author 应卓
 * @since 1.6.17
 */
public class CoreTarget<T> extends HardCodedTarget<T> {

    private final UrlSupplier urlSupplier;
    private final Class<?> clientType;
    private final Environment environment;

    public CoreTarget(Class<T> type, UrlSupplier urlSupplier, Environment environment) {
        super(Objects.requireNonNull(type), CoreTarget.class.getName());
        this.urlSupplier = Objects.requireNonNull(urlSupplier);
        this.clientType = type;
        this.environment = Objects.requireNonNull(environment);
    }

    @Override
    public String url() {
        String url = urlSupplier.get(clientType, environment);
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "http://" + url;
        }
        return url;
    }

}
