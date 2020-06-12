/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.openfeign;

import com.github.yingzhuo.carnival.openfeign.target.LazyTarget;
import com.github.yingzhuo.carnival.openfeign.target.UrlSupplier;
import org.springframework.beans.factory.FactoryBean;

import java.util.Objects;
import java.util.function.Supplier;

import static feign.Feign.Builder;

/**
 * @author 应卓
 * @since 1.6.16
 */
public class OpenFeignClientFactoryBean<T> implements FactoryBean<T> {

    private final Class<T> clientType;
    private final Builder builder;
    private final UrlSupplier urlSupplier;

    public OpenFeignClientFactoryBean(Class<T> clientType, Builder builder, UrlSupplier url) {
        this.clientType = Objects.requireNonNull(clientType);
        this.builder = builder;
        this.urlSupplier = url;
    }

    public OpenFeignClientFactoryBean(Class<T> clientType, Builder builder, String url) {
        this(clientType, builder, () -> url);
    }

    public OpenFeignClientFactoryBean(Class<T> clientType, Builder builder, Supplier<String> url) {
        this(clientType, builder, url::get);
    }

    @Override
    public T getObject() {
        return builder.target(LazyTarget.of(clientType, urlSupplier));
    }

    @Override
    public Class<?> getObjectType() {
        return clientType;
    }

}
