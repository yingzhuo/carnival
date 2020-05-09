/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.curator;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * @author 应卓
 * @since 1.3.0
 */
@Slf4j
public class CuratorFrameworkFactoryBean implements FactoryBean<CuratorFramework>, InitializingBean, DisposableBean {

    private final CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder();
    private CuratorFramework bean = null;
    private CuratorFrameworkFactoryBuilderConfigurer configurer;
    private String connectString;
    private int sessionTimeoutMs;
    private int connectionTimeoutMs;
    private String namespace;
    private RetryPolicy retryPolicy;

    public CuratorFrameworkFactoryBean() {
    }

    @Override
    public Class<?> getObjectType() {
        return CuratorFramework.class;
    }

    @Override
    public CuratorFramework getObject() {
        return bean;
    }

    @Override
    public void afterPropertiesSet() {

        builder.connectString(this.connectString);
        builder.sessionTimeoutMs(this.sessionTimeoutMs);
        builder.connectionTimeoutMs(this.connectionTimeoutMs);
        builder.retryPolicy(this.retryPolicy);

        if (namespace != null) {
            builder.namespace(namespace);
        }

        if (configurer != null) {
            configurer.config(builder);
        }

        bean = builder.build();
        bean.start();
    }

    @Override
    public void destroy() {
        if (bean != null) {
            bean.close();
        }
    }

    public void setConfigurer(CuratorFrameworkFactoryBuilderConfigurer configurer) {
        this.configurer = configurer;
    }

    public void setConnectString(String connectString) {
        this.connectString = connectString;
    }

    public void setSessionTimeoutMs(int sessionTimeoutMs) {
        this.sessionTimeoutMs = sessionTimeoutMs;
    }

    public void setConnectionTimeoutMs(int connectionTimeoutMs) {
        this.connectionTimeoutMs = connectionTimeoutMs;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public void setRetryPolicy(RetryPolicy retryPolicy) {
        this.retryPolicy = retryPolicy;
    }

}
