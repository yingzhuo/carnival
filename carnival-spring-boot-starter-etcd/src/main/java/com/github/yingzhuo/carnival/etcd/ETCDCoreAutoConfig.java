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

import lombok.Getter;
import lombok.Setter;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

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

    @Autowired(required = false)
    private ETCDClientCustomizer builderCustomizer;

    @Bean
    public ETCDClientFactory etcdClient() {
        val bean = new ETCDClientFactory();
        bean.setEndpoints(props.getEndpoints());
        bean.setCharset(props.getCharset());
        bean.setUsername(props.getUsername());
        bean.setPassword(props.getPassword());
        bean.setBuilderCustomizer(builderCustomizer);
        return bean;
    }

    @Override
    public void run(ApplicationArguments args) {
        ETCD.charset = props.getCharset();
    }

    @Getter
    @Setter
    @ConfigurationProperties(prefix = "carnival.etcd")
    static class Props {
        private boolean enabled = true;
        private Set<String> endpoints;
        private Charset charset = StandardCharsets.UTF_8;
        private String username;
        private String password;
    }

}
