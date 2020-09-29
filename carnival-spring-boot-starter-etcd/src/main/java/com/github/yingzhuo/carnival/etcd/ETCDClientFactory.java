/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.etcd;

import io.etcd.jetcd.ByteSequence;
import io.etcd.jetcd.Client;
import lombok.val;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import java.nio.charset.Charset;
import java.util.Set;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @author 应卓
 * @since 1.6.12
 */
class ETCDClientFactory implements FactoryBean<Client>, InitializingBean, DisposableBean {

    private Client client;
    private Set<String> endpoints;
    private String username;
    private String password;
    private Charset charset = UTF_8;
    private ETCDClientCustomizer builderCustomizer;

    @Override
    public Client getObject() {
        return this.client;
    }

    @Override
    public Class<?> getObjectType() {
        return Client.class;
    }

    @Override
    public void afterPropertiesSet() {
        val builder = Client.builder();

        builder.endpoints(endpoints.toArray(new String[0]));

        if (username != null && password != null) {
            builder.user(ByteSequence.from(username, charset));
            builder.password(ByteSequence.from(password, charset));
        }

        if (builderCustomizer != null) {
            builderCustomizer.customize(builder);
        }

        client = builder.build();
    }

    @Override
    public void destroy() {
        client.close();
    }

    public void setEndpoints(Set<String> endpoints) {
        this.endpoints = endpoints;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCharset(Charset charset) {
        this.charset = charset;
    }

    public void setBuilderCustomizer(ETCDClientCustomizer builderCustomizer) {
        this.builderCustomizer = builderCustomizer;
    }

}
