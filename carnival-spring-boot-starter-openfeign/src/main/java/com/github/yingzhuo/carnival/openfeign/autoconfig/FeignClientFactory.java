/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.openfeign.autoconfig;

import com.github.yingzhuo.carnival.openfeign.target.BrokenUrlSupplier;
import com.github.yingzhuo.carnival.openfeign.target.CoreTarget;
import com.github.yingzhuo.carnival.openfeign.target.UrlSupplier;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import static feign.Feign.Builder;

/**
 * @author 应卓
 * @since 1.6.17
 */
class FeignClientFactory<T> implements FactoryBean<T>, ApplicationContextAware {

    private ApplicationContext applicationContext;
    private Class<T> clientType;
    private Class<? extends UrlSupplier> urlSupplierType;
    private String url;

    @Override
    public T getObject() {
        final Builder builder = getBuilder();
        return builder.target(CoreTarget.of(clientType, getUrlSupplier(urlSupplierType, url)));
    }

    @Override
    public Class<?> getObjectType() {
        return clientType;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    private Builder getBuilder() {
        try {
            return applicationContext.getBean(Builder.class);
        } catch (BeansException e) {
            return new Builder();
        }
    }

    private UrlSupplier getUrlSupplier(Class<? extends UrlSupplier> urlSupplierType, String url) {
        if (!"".equals(url)) {
            return UrlSupplier.of(url);
        }

        if (urlSupplierType != BrokenUrlSupplier.class) {
            return applicationContext.getBean(urlSupplierType);
        }

        throw new IllegalArgumentException();
    }

    public void setClientType(Class<T> clientType) {
        this.clientType = clientType;
    }

    public void setUrlSupplierType(Class<? extends UrlSupplier> urlSupplierType) {
        this.urlSupplierType = urlSupplierType;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
