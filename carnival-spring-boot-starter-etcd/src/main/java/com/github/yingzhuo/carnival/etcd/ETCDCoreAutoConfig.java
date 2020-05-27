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
import lombok.Getter;
import lombok.Setter;
import lombok.val;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Set;

/**
 * @author 应卓
 * @since 1.6.11
 */
@EnableConfigurationProperties(ETCDCoreAutoConfig.Props.class)
@ConditionalOnProperty(prefix = "carnival.etcd", name = "enabled", havingValue = "true", matchIfMissing = true)
class ETCDCoreAutoConfig implements ApplicationRunner {

    @Autowired
    private Props props;

    @Bean
    public ClientFactoryBean etcdClient() {
        val bean = new ClientFactoryBean();
        bean.charset = props.getCharset();
        bean.username = props.getUsername();
        bean.password = props.getPassword();
        bean.endpoints = props.getEndpoints();
        return bean;
    }

    @Override
    public void run(ApplicationArguments args) {
        // 初始化ETCD工具
        ETCD.charset = props.getCharset();
    }

    static class ClientFactoryBean implements FactoryBean<Client>, InitializingBean, DisposableBean {
        private Client client;
        private Set<String> endpoints;
        private String username;
        private String password;
        private Charset charset = StandardCharsets.UTF_8;

        @Override
        public Client getObject() {
            return client;
        }

        @Override
        public Class<?> getObjectType() {
            return Client.class;
        }

        @Override
        public void afterPropertiesSet() {
            val builder = Client.builder().endpoints(endpoints.toArray(new String[0]));

            if (username != null && password != null) {
                builder.user(ByteSequence.from(username, charset));
                builder.password(ByteSequence.from(password, charset));
            }

            client = builder.build();
        }

        @Override
        public void destroy() {
            client.close();
        }
    }

    @Getter
    @Setter
    @ConfigurationProperties(prefix = "carnival.etcd")
    static class Props implements Serializable {

        private boolean enabled = true;
        private Set<String> endpoints;
        private Charset charset = StandardCharsets.UTF_8;
        private String username;
        private String password;
    }

}
